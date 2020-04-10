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
import com.d2.pcu.fragments.PhotoAdapter;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.google.gson.Gson;

public class TempleContactsFragment extends BaseFragment {

    private FragmentTempleContactsBinding binding;
    private TempleContactsViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;

    private Temple temple;

    private PhotoAdapter adapter;

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

        adapter = new PhotoAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(TempleContactsViewModel.class);
        viewModel.setTemple(temple);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
        viewModel.setOnAdditionalFuncMapListener(onAdditionalFuncMapListener);



        binding.setLifecycleOwner(this);
        binding.setModel(viewModel);
        binding.setTemple(temple);
       // adapter.setUrls(temple.getImageUrls());



        binding.templeContactsPhotoRv.setAdapter(adapter);
        binding.templeContactsPhotoRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        new LinearSnapHelper().attachToRecyclerView(binding.templeContactsPhotoRv);

        viewModel.disableLoading();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
        onAdditionalFuncMapListener = (OnAdditionalFuncMapListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
        onLoadingStateChangedListener = null;
        onAdditionalFuncMapListener = null;
    }
}
