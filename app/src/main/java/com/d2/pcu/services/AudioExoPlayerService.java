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
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.DownloadUtil;
import com.d2.pcu.utils.exo.AudioOnlyRenderersFactory;
import com.d2.pcu.utils.exo.Mp3ExtractorsFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.PlayerNotificationManager.BitmapCallback;
import com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class AudioExoPlayerService extends LifecycleService {

    private SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private MediaSessionCompat mediaSession;
    private MediaSessionConnector mediaSessionConnector;
    private Repository repository;
    private CacheDataSourceFactory cacheDataSourceFactory;
    private DefaultTrackSelector trackSelector;

    @Override
    public void onCreate() {
        super.onCreate();
        final Context context = this;
        startPlayer(context);

        repository = App.getInstance().getRepositoryInstance();


    }

    @Override
    public void onDestroy() {
//        mediaSession.release();
//        mediaSessionConnector.setPlayer(null);
        playerNotificationManager.setPlayer(null);
        player.release();
        player = null;

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        handleIntent(intent);
        return super.onBind(intent);
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
                int position = exoBundle.getInt(Constants.EXO_POSITION, 0);

                if (player.isPlaying()) {
                    player.stop();
                }
                if (isMorning) {
                    repository.getTransport().getMorningServerPraysChannel().observe(
                            this, prayList -> {
                                if (CollectionUtils.isEmpty(prayList) || prayList.size() < position)
                                    return;
                                List<Pray> pl = new ArrayList<>();
                                for (int i = position; i < prayList.size(); i++) {
                                    if (TextUtils.isEmpty(prayList.get(i).getFile().getName()))
                                        continue;
                                    pl.add(prayList.get(i));
                                }
                                if (!CollectionUtils.isEmpty(pl)) {
                                    setupPlayList(pl);
                                }
                            }
                    );
                }
            }
        }
    }

    private void startPlayer(Context context) {
        trackSelector = new DefaultTrackSelector(context);
        player = new SimpleExoPlayer.Builder(context, new AudioOnlyRenderersFactory(context))
                .setTrackSelector(trackSelector)
                .build();
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(context, context.getString(R.string.app_name)),
                5000, 5000, true
        );
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context,
                null,
                httpDataSourceFactory);
        cacheDataSourceFactory = new CacheDataSourceFactory(
                DownloadUtil.getDownloadCache(),
                dataSourceFactory,
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
    }

    private void setupPlayList(List<Pray> prayList) {
        Timber.e("setup play list: %s", prayList.size());
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

        for (Pray pray : prayList) {
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory, new Mp3ExtractorsFactory())
                    .createMediaSource(pray.getUrl());
            Timber.e("url play: %s", pray.getUrl());
            concatenatingMediaSource.addMediaSource(mediaSource);
        }
        player.prepare(concatenatingMediaSource);
        player.setPlayWhenReady(true);

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                this,
                Constants.PLAYBACK_CHANNEL_ID,
                R.string.playback_channel_name,
                R.string.playback_channel_name,
                Constants.PLAYBACK_NOTIFICATION_ID,
                new MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {
                        return prayList.get(player.getCurrentWindowIndex()).getTitle();
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        return prayList.get(player.getCurrentWindowIndex()).getText();
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

        /*
        mediaSession = new MediaSessionCompat(context, MEDIA_SESSION_TAG);
        mediaSession.setActive(true);

        playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());

        mediaSessionConnector = new MediaSessionConnector(mediaSession);
        mediaSessionConnector.setQueueNavigator(new TimelineQueueNavigator(mediaSession) {
            @Override
            public MediaDescriptionCompat getMediaDescription(Player player, int windowIndex) {
                return Samples.getMediaDescription(context, SAMPLES[windowIndex]);
            }
        });

        mediaSessionConnector.setPlayer(player);
        */
    }
/*

    public class ExoServiceBinder extends Binder {

        */
/**
 * This method should be used only for setting the exoplayer instance.
 * If exoplayer's internal are altered or accessed we can not guarantee
 * things will work correctly.
 *//*

        public ExoPlayer getExoPlayerInstance() {
            return player;
        }
    }
*/


}
