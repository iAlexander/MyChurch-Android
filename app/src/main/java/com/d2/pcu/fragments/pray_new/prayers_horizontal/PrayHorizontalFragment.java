package com.d2.pcu.fragments.pray_new.prayers_horizontal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.d2.pcu.App;
import com.d2.pcu.databinding.FragmentPraysHorizontalBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.pray_new.PrayViewModel;
import com.d2.pcu.services.AudioExoPlayerService;
import com.d2.pcu.utils.Constants;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.util.Util;

public class PrayHorizontalFragment extends BaseFragment {

    private FragmentPraysHorizontalBinding binding;
    private PrayViewModel viewModel;
    private PrayHorizontalAdapter adapter;
    private int currentPlaying = -1;

    private MediaSessionConnector msc;
    private MediaControllerCompat.TransportControls tc;
    private SimpleExoPlayer player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPraysHorizontalBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new PrayHorizontalAdapter();

        viewModel = new ViewModelProvider(getActivity()).get(PrayViewModel.class);

        setViewModelListeners(viewModel);

        binding.setModel(viewModel);

        binding.prayersHorizontalVp.setAdapter(adapter);

        if (viewModel.getSelectedType().equals(Constants.PRAY_MORNING)) {
            adapter.setPrays(viewModel.getMorningPrays().getValue());
        } else {
            adapter.setPrays(viewModel.getEveningPrays().getValue());
        }
        if (Constants.AUDIO_ENABLED) {
            initPlayer();
            checkPlayerState(viewModel.getSelectedItem());
            binding.prayersHorizontalVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    checkPlayerState(position);
                }
            });
        } else {
            binding.playerControlView.setVisibility(View.GONE);
        }

        binding.prayersHorizontalVp.setCurrentItem(viewModel.getSelectedItem(), false);
    }

    private void initPlayer() {
        Intent intentS = new Intent(getContext(), AudioExoPlayerService.class);
        Util.startForegroundService(App.getInstance(), intentS);

        player = App.getInstance().getExoHelper().getPlayer();
        msc = App.getInstance().getExoHelper().getMediaSessionConnector();
        tc = msc.mediaSession.getController().getTransportControls();
        binding.playerControlView.setPlayer(player);
        binding.playerControlView.setShowShuffleButton(false);
        binding.playerControlView.setShowTimeoutMs(0);
        binding.playerControlView.setControlDispatcher(new ControlDispatcher() {
            @Override
            public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
                int position = binding.prayersHorizontalVp.getCurrentItem();
                boolean isMorning = viewModel.getSelectedType().equals(Constants.PRAY_MORNING);
                switch (player.getPlaybackState()) {
                    case Player.STATE_IDLE:
                    case Player.STATE_ENDED: {

                        startSingleTrack(isMorning, position);
                        currentPlaying = position;
                    }
                    case Player.STATE_READY:
                        if (playWhenReady) {
                            if (currentPlaying == position) {
                                tc.play();
                            } else {
                                startSingleTrack(isMorning, position);
                                currentPlaying = position;
                            }
                        } else {
                            tc.pause();
                        }
                    default: {

                    }
                }
                return true;
            }

            @Override
            public boolean dispatchSeekTo(Player player, int windowIndex, long positionMs) {
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

    private void checkPlayerState(int position) {
        if (!TextUtils.isEmpty(adapter.getItemByPosition(position).getFile().getName())) {
            binding.playerControlView.setVisibility(View.VISIBLE);
            if (player.isPlaying())
                tc.pause();
        } else {
            binding.playerControlView.setVisibility(View.GONE);
        }
    }

    private void startSingleTrack(boolean isMorning, int position) {
        Intent intent = new Intent(getContext(), AudioExoPlayerService.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ITEMS_KEY, isMorning);
        bundle.putInt(Constants.SINGLE_TRACK, position);
        intent.putExtra(Constants.EXO_BUNDLE_KEY, bundle);
        Util.startForegroundService(getContext(), intent);
    }

}
