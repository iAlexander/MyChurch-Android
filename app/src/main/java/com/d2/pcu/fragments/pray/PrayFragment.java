package com.d2.pcu.fragments.pray;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentPrayBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PrayFragment extends Fragment {

    private FragmentPrayBinding binding;
    private PrayViewModel viewModel;

    private PrayersAdapter adapter;

    public static PrayFragment newInstance() {
        return new PrayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pray, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PrayViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);

        adapter = new PrayersAdapter();

        binding.prayViewpager.setAdapter(adapter);

        new TabLayoutMediator(binding.prayTabs, binding.prayViewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                int[] tabsTitle = new int[] {R.string.pray_evening, R.string.pray_morning, R.string.pray_favorite};
                tab.setText(tabsTitle[position]);
            }
        }).attach();
    }

}
