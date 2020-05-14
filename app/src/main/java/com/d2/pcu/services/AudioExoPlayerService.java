/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.d2.pcu.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.exo.ExoHelper;
import com.d2.pcu.utils.exo.Mp3ExtractorsFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.PlayerNotificationManager.BitmapCallback;
import com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class AudioExoPlayerService extends LifecycleService {

    private ExoHelper exoHelper;

    private SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private MediaSessionCompat mediaSession;
    private MediaSessionConnector mediaSessionConnector;
    private Repository repository;
    private List<Pray> mediaList = new ArrayList<>();
//    private IBinder exoServiceBinder = new ExoServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        exoHelper = App.getInstance().getExoHelper();
        player = exoHelper.getPlayer();

        repository = App.getInstance().getRepositoryInstance();
        repository.getTransport().getPlayItemEvent().observe(this, playItem -> {
            preparePlayList(playItem.isMorning(), playItem.getPosition());
        });
    }

    @Override
    public void onDestroy() {
        if (playerNotificationManager != null)
            playerNotificationManager.setPlayer(null);

        exoHelper.onDestroy();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            Bundle exoBundle = intent.getBundleExtra(Constants.EXO_BUNDLE_KEY);
            if (exoBundle != null) {
                boolean isMorning = exoBundle.getBoolean(Constants.ITEMS_KEY, true);
                if (exoBundle.containsKey(Constants.SINGLE_TRACK)) {

                    int track = exoBundle.getInt(Constants.SINGLE_TRACK);
                    prepareSingleTrack(isMorning, track);
                } else {
                    int position = exoBundle.getInt(Constants.EXO_POSITION, 0);
                    preparePlayList(isMorning, position);
                }
            }
        }
    }

    private void preparePlayList(boolean isMorning, int position) {
        if (player.isPlaying()) {
            player.stop();
        }
        if (isMorning) {
            repository.getTransport().getMorningServerPraysChannel().observe(
                    this, prayList -> {
                        if (CollectionUtils.isEmpty(prayList) || prayList.size() < position)
                            return;
                        mediaList.clear();
                        for (int i = position; i < prayList.size(); i++) {
                            if (TextUtils.isEmpty(prayList.get(i).getFile().getName()))
                                continue;
                            mediaList.add(prayList.get(i));
                        }
                        if (!CollectionUtils.isEmpty(mediaList)) {
                            setupPlayList(mediaList);
                        }
                    }
            );
        }
    }

    private void prepareSingleTrack(boolean isMorning, int position) {
        if (player.isPlaying()) {
            player.stop();
        }
        if (isMorning) {
            repository.getTransport().getMorningServerPraysChannel().observe(
                    this, prayList -> {
                        if (CollectionUtils.isEmpty(prayList)
                                || prayList.size() < position
                                || TextUtils.isEmpty(prayList.get(position).getFile().getName()))
                            return;
                        mediaList.clear();
                        mediaList.add(prayList.get(position));
                        if (!CollectionUtils.isEmpty(mediaList)) {
                            setupPlayList(mediaList);
                        }
                    }
            );
        }
    }

    private void setupPlayList(List<Pray> prayList) {
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

        for (Pray pray : prayList) {
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(exoHelper.getCacheDataSourceFactory(), new Mp3ExtractorsFactory())
                    .createMediaSource(pray.getUrl());
            Timber.e("url play: %s", pray.getUrl());

            concatenatingMediaSource.addMediaSource(mediaSource);
        }
        player.prepare(concatenatingMediaSource);
        player.setPlayWhenReady(true);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                if (ExoPlaybackException.TYPE_SOURCE == error.type) {
                    if (player.getNextWindowIndex() != -1) {
                        int index = player.getCurrentWindowIndex();
                        concatenatingMediaSource.removeMediaSource(index);
                        prayList.remove(index);
                        player.prepare(concatenatingMediaSource, false, false);
                    }
                }
            }
        });

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                this,
                Constants.PLAYBACK_CHANNEL_ID,
                R.string.playback_channel_name,
                R.string.playback_channel_name,
                Constants.PLAYBACK_NOTIFICATION_ID,
                new MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {
                        if (prayList.size() > player.getCurrentWindowIndex())
                            return prayList.get(player.getCurrentWindowIndex()).getTitle();
                        else return "";
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        if (prayList.size() > player.getCurrentWindowIndex())
                            return prayList.get(player.getCurrentWindowIndex()).getText();
                        else return "";
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, BitmapCallback callback) {
                        return Pray.getBitmap(
                                App.getInstance().getApplicationContext(), R.drawable.ic_news_active);
                    }
                }, new PlayerNotificationManager.NotificationListener() {
                    @Override
                    public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
                        stopSelf();
                    }

                    @Override
                    public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
//                        startForeground(notificationId, notification);
                    }
                }
        );
        playerNotificationManager.setPlayer(player);

        mediaSession = exoHelper.getMediaSession();
        mediaSession.setActive(true);

        playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());

        mediaSessionConnector = exoHelper.getMediaSessionConnector();
        mediaSessionConnector.setQueueNavigator(new TimelineQueueNavigator(mediaSession) {
            @Override
            public MediaDescriptionCompat getMediaDescription(Player player, int windowIndex) {
                return Pray.getMediaDescription(AudioExoPlayerService.this, mediaList.get(windowIndex));
            }
        });

        mediaSessionConnector.setPlayer(player);
//        mediaSession.setSessionActivity();
    }
}
