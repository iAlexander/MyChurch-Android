package com.d2.pcu.fragments.map.temple;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.FragmentCathedralContactBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

public class TempleFragment extends BaseFragment {

    private TempleViewModel viewModel;
    private FragmentCathedralContactBinding binding;

    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;

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
        binding = FragmentCathedralContactBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TempleViewModel.class);

        setViewModelListeners(viewModel);

        binding.setTemple(temple);
        setDataToList();
        viewModel.disableLoading();

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);

        viewModel.disableLoading();
    }

    private void setDataToList() {
        binding.templeViewpager.setAdapter(new TempleViewPagerAdapter(getContext(), temple, onAdditionalFuncMapListener));

        new TabLayoutMediator(binding.templeTabs, binding.templeViewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                int[] tabsTitle = {R.string.temple_contacts, R.string.temple_history_descr};

                tab.setText(tabsTitle[position]);
            }
        }).attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onAdditionalFuncMapListener = (OnAdditionalFuncMapListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onAdditionalFuncMapListener = null;
    }
}
