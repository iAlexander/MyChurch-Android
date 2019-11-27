package com.d2.pcu.fragments.map.temple;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2.pcu.databinding.TempleFragmentBinding;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

public class TempleFragment extends BaseFragment {

    public static final String ARG_TEMPLE = "temple";

    private TempleViewModel viewModel;
    private TempleFragmentBinding binding;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private Temple temple;

    public static TempleFragment newInstance() {
        return new TempleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            temple = new Gson().fromJson(TempleFragmentArgs.fromBundle(getArguments()).getSerializedTemple(), Temple.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.temple_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TempleViewModel.class);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);

        viewModel.enableLoading();
        viewModel.loadTempleInfoById(temple.getId());

        viewModel.getTempleLiveData().observe(getViewLifecycleOwner(), new Observer<Temple>() {
            @Override
            public void onChanged(Temple temple) {
                TempleFragment.this.temple = temple;

                binding.setTemple(temple);
                setDataToList();
                viewModel.disableLoading();
            }
        });

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);


    }

    private void setDataToList() {
        binding.templeViewpager.setAdapter(new TempleViewPagerAdapter(getContext(), temple));

        new TabLayoutMediator(binding.templeTabs, binding.templeViewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                int[] tabsTitle = {R.string.temple_contacts, R.string.temple_history_descr} ;

                tab.setText(tabsTitle[position]);
            }
        }).attach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
        onLoadingStateChangedListener = null;
    }
}
