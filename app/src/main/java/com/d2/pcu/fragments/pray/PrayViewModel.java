package com.d2.pcu.fragments.pray;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.services.PrayerDownloadService;
import com.d2.pcu.fragments.pray.utils.CombinedPraysLiveData;
import com.d2.pcu.utils.Constants;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.offline.DownloadRequest;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrayViewModel extends BaseViewModel {

//    private static final String TAG = PrayViewModel.class.getSimpleName();
//
//    private Repository repository;
//    private DataSource.Factory dataSourceFactory;
//    private CacheDataSourceFactory chacheDataSourceFactory;
//
//    //Player settings
//    private List<Player.EventListener> playerEventListeners;
//
//    private boolean playWhenReady = false;
//    private int currentWindow = 0;
//    private long playbackPosition = 0;
//
//    private SimpleExoPlayer player;
//    //
//
////    private LiveData<List<Pray>> morningPrays;
////    private LiveData<List<Pray>> eveningPrays;
//
//    private MutableLiveData<ConcatenatingMediaSource> morningMediaSource = new MutableLiveData<>();
//    private MutableLiveData<ConcatenatingMediaSource> eveningMediaSource = new MutableLiveData<>();
//    private MutableLiveData<List<MediaSource>> downloadedMediaSources = new MutableLiveData<>();
//
//    private List<Pray> currentSelectedTabPrayList;
//    private ConcatenatingMediaSource currentPlayingSource;
//
//    //data binding
//    private MutableLiveData<String> prayTitle = new MutableLiveData<>("");
//    private MutableLiveData<String> prayText = new MutableLiveData<>("");
//
//    private MutableLiveData<Boolean> isScreenAlwaysOn = new MutableLiveData<>(false);
//    private MutableLiveData<Boolean> isPlay = new MutableLiveData<>(false);
//    private MutableLiveData<Boolean> downlodable = new MutableLiveData<>(false);
//    private MutableLiveData<Boolean> downloaded = new MutableLiveData<>(false);
//    //
//
//    private boolean morningTab = true;
//    private int currentPlayingPosition = -1;
//
//    //TEMP
//    private CombinedPraysLiveData combinedMorningPraysLiveData;
//    private CombinedPraysLiveData combinedEveningPraysLiveData;
//
//    public PrayViewModel() {
//        repository = App.getInstance().getRepositoryInstance();
//        currentSelectedTabPrayList = new ArrayList<>();
//        downloadedMediaSources.setValue(new ArrayList<>());
//        playerEventListeners = new ArrayList<>();
//
//        combinedMorningPraysLiveData = new CombinedPraysLiveData(
//                repository.getTransport().getMorningServerPraysChannel(),
//                repository.getTransport().getMorningDBPraysChannel()
//        );
//
//        combinedEveningPraysLiveData = new CombinedPraysLiveData(
//                repository.getTransport().getEveningServerPraysChannel(),
//                repository.getTransport().getEveningDBPraysChannel()
//        );
//
//        loadData();
//    }
//
//    void loadData() {
//        repository.getPrays(0, 100, Constants.PRAY_MORNING);
//        repository.getPrays(0, 100, Constants.PRAY_EVENING);
//
////        repository.getPraysFromDb(Constants.PRAY_MORNING);
////        repository.getPraysFromDb(Constants.PRAY_EVENING);
//    }
//
//    /**
//     * React on changes at view
//     *
//     * @param morningTab describe at what tab user now
//     */
//    void setMorningTab(boolean morningTab) {
//        this.morningTab = morningTab;
//    }
//
//    CombinedPraysLiveData getCombinedMorningPraysLiveData() {
//        return combinedMorningPraysLiveData;
//    }
//
//    CombinedPraysLiveData getCombinedEveningPraysLiveData() {
//        return combinedEveningPraysLiveData;
//    }
//
//    /**
//     * Getters
//     */
//    public MutableLiveData<String> getPrayTitle() {
//        return prayTitle;
//    }
//
//    public MutableLiveData<String> getPrayText() {
//        return prayText;
//    }
//
//    public MutableLiveData<Boolean> getIsScreenAlwaysOn() {
//        return isScreenAlwaysOn;
//    }
//
//    MutableLiveData<Boolean> getIsPlay() {
//        return isPlay;
//    }
//
//    int getCurrentPlayingPosition() {
//        return currentPlayingPosition;
//    }
//
//    public MutableLiveData<Boolean> getDownlodable() {
//        return downlodable;
//    }
//
//    public LiveData<Boolean> isDownloaded() {
//        return downloaded;
//    }
//    //
//
//    /**
//     * Check if player null initialize it using context
//     * If current playing source not null prepare it for play
//     * That need for restoring player when resume was called
//     * not at first.
//     *
//     * @param context need for player init
//     */
//    private void initializePlayer(Context context) {
//        if (player == null) {
//            DefaultTrackSelector defaultTrackSelector = new DefaultTrackSelector(context);
//            defaultTrackSelector.setParameters(defaultTrackSelector.buildUponParameters());
//
//            player = new SimpleExoPlayer.Builder(context)
//                    .setTrackSelector(defaultTrackSelector)
//                    .build();
//
//            if (currentPlayingSource != null) {
//                player.prepare(currentPlayingSource);
//            }
//            player.setPlayWhenReady(playWhenReady);
//            player.seekTo(currentWindow, playbackPosition);
//
//            addPlayerEventListeners();
//        }
//    }
//
//    /**
//     * Method witch add listeners to player instance
//     */
//    private void addPlayerEventListeners() {
//        playerEventListeners.add(new Player.EventListener() {
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                if (playbackState == Player.STATE_IDLE
//                        || playbackState == Player.STATE_ENDED
//                        || !playWhenReady) {
//
//                    isScreenAlwaysOn.setValue(false);
//                } else {
//                    isScreenAlwaysOn.setValue(true);
//                }
//            }
//        });
//
//        playerEventListeners.add(new Player.EventListener() {
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//                currentPlayingPosition = player.getCurrentWindowIndex();
//
//                prayTitle.setValue(currentSelectedTabPrayList.get(currentPlayingPosition).getTitle());
//                prayText.setValue(currentSelectedTabPrayList.get(currentPlayingPosition).getText());
//                downloaded.setValue(currentSelectedTabPrayList.get(currentPlayingPosition).isDownloaded());
//            }
//        });
//
//        playerEventListeners.add(new Player.EventListener() {
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                if (currentPlayingSource == null) {
//                    onPlayListClick();
//                    return;
//                }
//                isPlay.setValue(playWhenReady);
//            }
//        });
//
//        addEventListenersToPlayer();
//    }
//
//    private void addEventListenersToPlayer() {
//        if (player != null) {
//            for (Player.EventListener eventListener : playerEventListeners) {
//                player.addListener(eventListener);
//            }
//        }
//    }
//
//    SimpleExoPlayer getInitializedPlayer(Context context) {
//        if (player == null) {
//            initializePlayer(context);
//        }
//        return player;
//    }
//
//    private void removeListenersFromPlayer() {
//        if (player != null) {
//            for (Player.EventListener eventListener : playerEventListeners) {
//                player.removeListener(eventListener);
//            }
//        }
//    }
//
//    /**
//     * Using this method, when fragment destroy view
//     */
//    void releasePlayer() {
//        if (player != null) {
//            playWhenReady = player.getPlayWhenReady();
//            playbackPosition = player.getContentPosition();
//            currentWindow = player.getCurrentWindowIndex();
//            removeListenersFromPlayer();
//            stopPlaying();
//            player.release();
//            player = null;
//        }
//    }
//
//    /**
//     * Method for ConcatenatingMediaSource creation
//     * When we update resources from server we collect
//     * source data from it
//     *
//     * @param prays     - data from server
//     * @param isMorning - type of data
//     * @param context   - for factory creating
//     */
//    void collectMediaSourceCollection(List<Pray> prays, boolean isMorning, Context context) {
//        if (prays.size() == 0) {
//            return;
//        }
//
//        List<MediaSource> mediaSources = new ArrayList<>();
//        if (dataSourceFactory == null) {
//            dataSourceFactory = new DefaultDataSourceFactory(
//                    context,
//                    context.getResources().getString(R.string.app_name)
//            );
//        }
//
//        ProgressiveMediaSource.Factory mediaSourceFactory =
//                new ProgressiveMediaSource.Factory(dataSourceFactory);
//
//
//        for (Pray pray : prays) {
//            if (pray.getUrlMP3() == null || pray.getUrlMP3().isEmpty()) {
//                continue;
//            }
//
//            if (pray.isDownloaded()) {
//                mediaSources.add(createOfflineMediaSource(pray, context));
//                continue;
//            }
//
//            MediaSource mediaSource =
//                    mediaSourceFactory.createMediaSource(Uri.parse(pray.getUrlMP3()));
//
//            mediaSources.add(mediaSource);
//        }
//
//        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
//        concatenatingMediaSource.addMediaSources(mediaSources);
//
//        if (isMorning) {
//            morningMediaSource.setValue(concatenatingMediaSource);
//            setCurrentMedia();
//        } else {
//            eveningMediaSource.setValue(concatenatingMediaSource);
//        }
//
//    }
//
//    /**
//     * Method for all track list preparing
//     */
//    void onPlayListClick() {
//        setCurrentMedia();
//        player.setPlayWhenReady(true);
//    }
//
//    /**
//     * Method for preparing track list and
//     * set one using
//     *
//     * @param position for track change
//     */
//    void changeTrackTo(int position) {
//        setCurrentMedia();
//
//        if (currentSelectedTabPrayList.size() > position) {
//            player.seekTo(position, 0);
//            player.setPlayWhenReady(true);
//        } else {
//            stopPlaying();
//        }
//    }
//
//    private void setCurrentMedia() {
//        if (morningTab) {
//            if (morningMediaSource.getValue() != null) {
////                currentSelectedTabPrayList = morningPrays.getValue();
//                currentSelectedTabPrayList.clear();
//                currentSelectedTabPrayList.addAll(combinedMorningPraysLiveData.getValue().first);
//                currentSelectedTabPrayList.addAll(combinedMorningPraysLiveData.getValue().second);
//
//                sortListById(currentSelectedTabPrayList);
//
//                currentPlayingSource = morningMediaSource.getValue();
//            }
//        } else {
//            if (eveningMediaSource.getValue() != null) {
////                currentSelectedTabPrayList = eveningPrays.getValue();
//                currentSelectedTabPrayList.clear();
//                currentSelectedTabPrayList.addAll(combinedEveningPraysLiveData.getValue().first);
//                currentSelectedTabPrayList.addAll(combinedEveningPraysLiveData.getValue().second);
//
//                sortListById(currentSelectedTabPrayList);
//
//                currentPlayingSource = eveningMediaSource.getValue();
//            }
//        }
//        if (currentPlayingSource != null) {
//            player.prepare(currentPlayingSource);
//        }
//    }
//
//    void pauseOrUnPausePlaying() {
//        player.setPlayWhenReady(!player.getPlayWhenReady());
//    }
//
//    void stopPlaying() {
//        player.stop();
//    }
//
//    void downloadFile(Context context) {
//        if (currentSelectedTabPrayList != null) {
//            Pray pray = currentSelectedTabPrayList.get(currentPlayingPosition);
//
//
//            if (!pray.isDownloaded()) {
//                String contentId;
//                if (morningTab) {
//                    contentId = pray.getId() + "_morning";
//                } else {
//                    contentId = pray.getId() + "_evening";
//                }
//                Uri contentUri = Uri.parse(pray.getUrlMP3());
//
//                DownloadRequest downloadRequest = new DownloadRequest(
//                        contentId,
//                        DownloadRequest.TYPE_PROGRESSIVE,
//                        contentUri,
//                        Collections.emptyList(),
//                        null,
//                        null
//                );
//
//                DownloadService.sendAddDownload(
//                        context,
//                        PrayerDownloadService.class,
//                        downloadRequest,
//                        false
//                );
//
//                pray.setDownloaded(true);
//                repository.savePrayToDb(pray);
//            }
//        }
//    }
//
//    void removeDownloadFile(Context context) {
//        Pray pray = (currentSelectedTabPrayList.get(currentPlayingPosition));
//        String contentId = String.valueOf(pray.getId());
//
//        DownloadService.sendRemoveDownload(
//                context,
//                PrayerDownloadService.class,
//                contentId,
//                false
//        );
//
//        pray.setDownloaded(false);
//        repository.removePrayFromDb(pray);
//    }
//
//    private MediaSource createOfflineMediaSource(Pray pray, Context context) {
//
//        Uri contentUri = Uri.parse(pray.getUrlMP3());
//
//        if (chacheDataSourceFactory == null) {
//            chacheDataSourceFactory = new CacheDataSourceFactory(
//                    App.getInstance().getDownloadCache(), new DefaultDataSourceFactory(context, context.getResources().getString(R.string.app_name))
//            );
//        }
//
//        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(chacheDataSourceFactory).createMediaSource(contentUri);
//        return mediaSource;
//    }
//
//    void sortListById(List<Pray> prays) {
//        Collections.sort(
//                prays,
//                (o1, o2) -> Integer.compare(o1.getId(), o2.getId())
//        );
//    }
}
