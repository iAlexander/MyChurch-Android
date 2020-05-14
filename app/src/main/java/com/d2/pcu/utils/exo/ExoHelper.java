package com.d2.pcu.utils.exo;

import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;

import com.d2.pcu.R;
import com.d2.pcu.utils.DownloadUtil;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.d2.pcu.utils.Constants.MEDIA_SESSION_TAG;

public final class ExoHelper {

    private Context exoContext;
    private static final int CONNECT_TIMEOUT_MILLIS = 5000;
    private static final int READ_TIMEOUT_MILLIS = 5000;
    private SimpleExoPlayer player;
    private MediaSessionCompat mediaSession;
    private MediaSessionConnector mediaSessionConnector;
    private CacheDataSourceFactory cacheDataSourceFactory;

    public ExoHelper(Context context) {
        this.exoContext = context;
    }

    private void initPlayer() {
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(exoContext);
        player = new SimpleExoPlayer.Builder(exoContext, new AudioOnlyRenderersFactory(exoContext))
                .setTrackSelector(trackSelector)
                .build();
    }

    public SimpleExoPlayer getPlayer() {
        if (player == null) {
            initPlayer();
        }
        return player;
    }

    public CacheDataSourceFactory getCacheDataSourceFactory() {
        if (cacheDataSourceFactory == null) {
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                    Util.getUserAgent(exoContext, exoContext.getString(R.string.app_name)),
                    CONNECT_TIMEOUT_MILLIS, READ_TIMEOUT_MILLIS, true
            );
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(exoContext,
                    null,
                    httpDataSourceFactory);
            cacheDataSourceFactory = new CacheDataSourceFactory(
                    DownloadUtil.getDownloadCache(),
                    dataSourceFactory,
                    CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
        }
        return cacheDataSourceFactory;
    }

    public MediaSessionCompat getMediaSession() {
        if (mediaSession == null)
            mediaSession = new MediaSessionCompat(exoContext, MEDIA_SESSION_TAG);
        return mediaSession;
    }

    public MediaSessionConnector getMediaSessionConnector() {
        if (mediaSessionConnector == null) {
            mediaSessionConnector = new MediaSessionConnector(getMediaSession());
        }

        return mediaSessionConnector;
    }

    public void onDestroy() {
        if (mediaSession != null) {
            mediaSession.release();
            mediaSession = null;
        }
        if (mediaSessionConnector != null) {
            mediaSessionConnector.setPlayer(null);
            mediaSessionConnector = null;
        }
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
