package com.d2.pcu.fragments.pray;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.d2.pcu.R;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.databinding.FragmentPrayBinding;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.services.PrayerDownloadService;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrayFragment extends Fragment {

//    private static final String TAG = PrayFragment.class.getSimpleName();

    private FragmentPrayBinding binding;
    private PrayViewModel viewModel;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private PrayersAdapter adapter;

//    public static PrayFragment newInstance() {
//        return new PrayFragment();
//    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        initializePlayer();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pray, container, false);
//        return binding.getRoot();
//    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PrayViewModel.class);
//        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
//
//        binding.setLifecycleOwner(this);
//        binding.setModel(viewModel);
//        viewModel.enableLoading();
//
//        assembleViewPagerAndTabs();
//
//        viewModel.getCombinedMorningPraysLiveData().observe(getViewLifecycleOwner(), listListPair -> {
//            List<Pray> prays = new ArrayList<>();
//
//            if (listListPair.first != null) {
//                prays.addAll(listListPair.first);
//            }
//
//            if (listListPair.second != null) {
//                prays.addAll(listListPair.second);
//            }
//
//            viewModel.sortListById(prays);
//
//            adapter.setMorningPrays(prays);
//
//            viewModel.collectMediaSourceCollection(prays, true, getContext());
//
//            viewModel.disableLoading();
//        });
//
//        viewModel.getCombinedEveningPraysLiveData().observe(getViewLifecycleOwner(), listListPair -> {
//            List<Pray> prays = new ArrayList<>();
//
//            if (listListPair.first != null) {
//                prays.addAll(listListPair.first);
//            }
//
//            if (listListPair.second != null) {
//                prays.addAll(listListPair.second);
//            }
//
//            viewModel.sortListById(prays);
//
//            adapter.setEveningPrays(prays);
//
//            viewModel.collectMediaSourceCollection(prays, false, getContext());
//        });
//
//        viewModel.getIsPlay().observe(getViewLifecycleOwner(), aBoolean -> {
//            if (aBoolean) {
//                binding.dragPlayIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black));
//            } else {
//                binding.dragPlayIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black));
//            }
//        });
//
//        binding.dragPlayIv.setOnClickListener(view -> {
//            if (viewModel.getCurrentPlayingPosition() != -1) {
//                viewModel.pauseOrUnPausePlaying();
//                return;
//            }
//
//            viewModel.onPlayListClick();
//        });
//
//        binding.slidingUpPanelL.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
//            @Override
//            public void onPanelSlide(View panel, float slideOffset) {
//            }
//
//            @Override
//            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
//
//            }
//        });
//
//        binding.downloadSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    viewModel.downloadFile(getContext());
//                } else {
//                    viewModel.removeDownloadFile(getContext());
//                }
//            }
//        });
//
//        initializePlayer();
//    }
//
//    private void assembleViewPagerAndTabs() {
//        adapter = new PrayersAdapter(
//                (pray, position) -> {
//                    if (pray.getFile() != null && !pray.getFile().getName().isEmpty()) {
//                        binding.dragPlayIv.setVisibility(View.VISIBLE);
//                        binding.playerV.setVisibility(View.VISIBLE);
//                        viewModel.changeTrackTo(position);
//                        viewModel.getDownlodable().setValue(true);
//                    } else {
//                        viewModel.stopPlaying();
//                        binding.prayTitleTv.setText(pray.getTitle());
//                        binding.contentPrayTextTv.setText(pray.getText());
//                        binding.dragPlayIv.setVisibility(View.INVISIBLE);
//                        binding.playerV.setVisibility(View.GONE);
//                        viewModel.getDownlodable().setValue(false);
//                    }
//                    binding.slidingUpPanelL.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
//                },
//                new OnRefreshPraysListener() {
//                    @Override
//                    public void update() {
//                        viewModel.loadData();
//                    }
//                }
//        );
//
//        binding.prayViewpager.setAdapter(adapter);
//
//        new TabLayoutMediator(binding.prayTabs, binding.prayViewpager, (tab, position) -> {
//            int[] tabsTitle = new int[]{R.string.pray_morning, R.string.pray_evening};
//            tab.setText(tabsTitle[position]);
//        }).attach();
//
//        binding.prayTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 0) {
//                    viewModel.setMorningTab(true);
//                } else {
//                    viewModel.setMorningTab(false);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//
//
//        try {
//            DownloadService.start(getContext(), PrayerDownloadService.class);
//        } catch (IllegalStateException e) {
//            DownloadService.startForeground(getContext(), PrayerDownloadService.class);
//        }
//    }
//
//
//    private void initializePlayer() {
//        if (viewModel != null) {
//            binding.playerV.setPlayer(viewModel.getInitializedPlayer(getContext()));
//        }
//    }
//
//    private void releasePlayer() {
//        if (viewModel != null) {
//            viewModel.releasePlayer();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (Util.SDK_INT < 24) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (Util.SDK_INT >= 24) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        onLoadingStateChangedListener = null;
//    }
}
