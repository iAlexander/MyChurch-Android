package com.d2.pcu.fragments.pray_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.R;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.FragmentPrayersVerticalBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.pray.OnPrayItemClickListener;
import com.d2.pcu.fragments.pray.OnRefreshPraysListener;
import com.d2.pcu.listeners.OnAdditionalFuncPrayersListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.services.AudioExoPlayerService;
import com.d2.pcu.utils.Constants;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.tabs.TabLayoutMediator;

import timber.log.Timber;

public class PrayVerticalFragment extends BaseFragment {

    private FragmentPrayersVerticalBinding binding;
    private PrayViewModel viewModel;

    private PrayersVerticalAdapter adapter;

    private OnAdditionalFuncPrayersListener onAdditionalFuncPrayersListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    /*
        private ExoPlayer player;
        private ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (service instanceof AudioExoPlayerService.ExoServiceBinder) {
                    //Then we simply set the exoplayer instance on this view.
                    //Notice we are only getting information.
                    player = ((AudioExoPlayerService.ExoServiceBinder) service).getExoPlayerInstance();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prayers_vertical, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(PrayViewModel.class);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
        viewModel.enableLoading();

/*
        Intent intentS = new Intent(getContext(), AudioExoPlayerService.class);
        getActivity().bindService(intentS, serviceConnection, Context.BIND_AUTO_CREATE);
        */

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
                    Timber.e("start player");
                    Intent intent = new Intent(getContext(), AudioExoPlayerService.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constants.ITEMS_KEY, true);
                    bundle.putInt(Constants.EXO_POSITION, position);
                    intent.putExtra(Constants.EXO_BUNDLE_KEY, bundle);
                    Util.startForegroundService(getContext(), intent);
/*

                    binding.playerControlView.setPlayer(player);
                    player.addListener(new Player.EventListener() {
                        @Override
                        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        }
                    });
                    binding.playerControlView.setShowShuffleButton(false);
                    binding.playerControlView.setControlDispatcher(new ControlDispatcher() {
                        @Override
                        public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
                            return false;
                        }

                        @Override
                        public boolean dispatchSeekTo(Player player, int windowIndex, long positionMs) {
                            return false;
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
                    binding.playerControlView.setVisibility(View.VISIBLE);
*/

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
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onAdditionalFuncPrayersListener = (OnAdditionalFuncPrayersListener) context;
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onAdditionalFuncPrayersListener = null;
        onLoadingStateChangedListener = null;
    }
}
