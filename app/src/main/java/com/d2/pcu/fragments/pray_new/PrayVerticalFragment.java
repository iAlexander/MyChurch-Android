package com.d2.pcu.fragments.pray_new;

import android.content.Context;
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
import com.google.android.material.tabs.TabLayoutMediator;

public class PrayVerticalFragment extends BaseFragment {

    private FragmentPrayersVerticalBinding binding;
    private PrayViewModel viewModel;

    private PrayersVerticalAdapter adapter;

    private OnAdditionalFuncPrayersListener onAdditionalFuncPrayersListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;

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
