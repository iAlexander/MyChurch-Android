package com.d2.pcu.fragments.pray_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.FragmentPrayersVerticalBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.pray.OnPrayItemClickListener;
import com.d2.pcu.fragments.pray.OnRefreshPraysListener;
import com.d2.pcu.listeners.OnAdditionalFuncPrayersListener;
import com.d2.pcu.services.AudioExoPlayerService;
import com.d2.pcu.utils.Constants;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.tabs.TabLayoutMediator;

import timber.log.Timber;

public class PrayVerticalFragment extends BaseFragment {

    private FragmentPrayersVerticalBinding binding;
    private PrayViewModel viewModel;

    private PrayersVerticalAdapter adapter;

    private OnAdditionalFuncPrayersListener onAdditionalFuncPrayersListener;

    private MediaSessionConnector msc;

    private ExoPlayer player;
    private MediaControllerCompat.TransportControls tc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPrayersVerticalBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(PrayViewModel.class);
        setViewModelListeners(viewModel);
        binding.setModel(viewModel);

        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count ->
                binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active));
        viewModel.enableLoading();

//        Intent intentS = new Intent(getContext(), AudioExoPlayerService.class);
//        Util.startForegroundService(App.getInstance(), intentS);

        adapter = new PrayersVerticalAdapter(
                new OnPrayItemClickListener() {
                    @Override
                    public void onPrayClick(Pray pray, int position) {
                        viewModel.selectedItem = position;
                        viewModel.selectedType = pray.getType();
                        if (onAdditionalFuncPrayersListener != null) {
                            onAdditionalFuncPrayersListener.onPrayItemClick();
                        }
                    }
                },
                new OnRefreshPraysListener() {
                    @Override
                    public void update() {
                        viewModel.loadPrays();
                    }
                },
                (prays, position) -> {
                    if (Constants.AUDIO_ENABLED) {
                        Timber.e("start player");
                        startPlaylistFromPosition(position);
                    }
                }
        );

        viewModel.getMorningPrays().observe(getViewLifecycleOwner(), morningPrays -> {
            adapter.setMorningPrays(morningPrays);
            viewModel.disableLoading();
        });

        viewModel.getEveningPrays().observe(getViewLifecycleOwner(), eveningPrays -> {
            adapter.setEveningPrays(eveningPrays);
        });

        binding.prayersVerticalViewpager.setAdapter(adapter);

        new TabLayoutMediator(binding.prayerVerticalTabs, binding.prayersVerticalViewpager, (tab, position) -> {
            int[] tabsTitle = new int[]{R.string.pray_morning, R.string.pray_evening};
            tab.setText(tabsTitle[position]);
        }).attach();

        if (Constants.AUDIO_ENABLED) {
            initPlayer();
        } else {
            binding.playerControlView.setVisibility(View.GONE);
        }
    }

    private void startPlaylistFromPosition(int position) {
        Intent intent = new Intent(getContext(), AudioExoPlayerService.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ITEMS_KEY, true);
        bundle.putInt(Constants.EXO_POSITION, position);
        intent.putExtra(Constants.EXO_BUNDLE_KEY, bundle);
        Util.startForegroundService(getContext(), intent);
    }

    private void initPlayer() {
        player = App.getInstance().getExoHelper().getPlayer();
        msc = App.getInstance().getExoHelper().getMediaSessionConnector();
        tc = msc.mediaSession.getController().getTransportControls();
        binding.playerControlView.setPlayer(player);
        binding.playerControlView.setShowShuffleButton(false);
        binding.playerControlView.setShowTimeoutMs(0);
        binding.playerControlView.setVisibility(View.VISIBLE);

        // TODO: 4/21/20 add title is playing

        player.addListener(new Player.EventListener() {


            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (!playWhenReady && playbackState == Player.STATE_ENDED || playbackState == Player.STATE_IDLE) {
                }
                // TODO: 4/21/20 implement IDLE && ENDED

//                            if (mediaList != null)
//                                binding.playbackTitle.setText(mediaList.get(player.getCurrentWindowIndex()).getTitle());
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Timber.e(error, "player error: %s", error.type);
            }
        });

        binding.playerControlView.setControlDispatcher(new ControlDispatcher() {
            @Override
            public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
                // TODO: 4/21/20 implement IDLE && ENDED
                switch (player.getPlaybackState()) {
                    case Player.STATE_IDLE:
                    case Player.STATE_ENDED: {
                        startPlaylistFromPosition(0);
                    }
                    default: {
                        if (playWhenReady) {
                            tc.play();
                        } else {
                            tc.pause();
                        }
                    }
                }

                return true;
            }

            @Override
            public boolean dispatchSeekTo(Player player, int windowIndex, long positionMs) {

                //skip only in fragment
                tc.skipToQueueItem(windowIndex);
                tc.seekTo(positionMs);
                return true;
            }

            @Override
            public boolean dispatchSetRepeatMode(Player player, int repeatMode) {
                return false;
            }

            @Override
            public boolean dispatchSetShuffleModeEnabled(Player player, boolean shuffleModeEnabled) {
                return false;
            }

            @Override
            public boolean dispatchStop(Player player, boolean reset) {

                return false;
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onAdditionalFuncPrayersListener = (OnAdditionalFuncPrayersListener) context;
    }

    @Override
    public void onDetach() {
        onAdditionalFuncPrayersListener = null;
        super.onDetach();
    }
}
