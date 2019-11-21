package com.d2.pcu.fragments.map.temple.temple_views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.FragmentTempleContactsBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingEnableListener;
import com.google.gson.Gson;

import java.util.Arrays;

public class TempleContactsFragment extends BaseFragment {

    private FragmentTempleContactsBinding binding;
    private TempleContactsViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingEnableListener onLoadingEnableListener;

    private Temple temple;

    private TemplePhotoAdapter adapter;

    public static TempleContactsFragment newInstance() {
        return new TempleContactsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            temple = new Gson().fromJson(TempleContactsFragmentArgs.fromBundle(getArguments()).getSerializedTemple(), Temple.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_temple_contacts, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TemplePhotoAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(TempleContactsViewModel.class);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setOnLoadingEnableListener(onLoadingEnableListener);
        viewModel.enableLoading();
        viewModel.loadTempleInfoById(temple.getId());

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);

        viewModel.getTempleLiveData().observe(getViewLifecycleOwner(), new Observer<Temple>() {
            @Override
            public void onChanged(Temple temple) {
                TempleContactsFragment.this.temple = temple;
                binding.setTemple(temple);
                adapter.setUrls(Arrays.asList(temple.getImageUrl()));

                viewModel.disableLoading();
            }
        });

        binding.templeContactsPhotoRv.setAdapter(adapter);
        binding.templeContactsPhotoRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        new LinearSnapHelper().attachToRecyclerView(binding.templeContactsPhotoRv);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoadingEnableListener = (OnLoadingEnableListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
        onLoadingEnableListener = null;
    }
}
