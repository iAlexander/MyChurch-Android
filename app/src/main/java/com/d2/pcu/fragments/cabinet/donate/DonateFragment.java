package com.d2.pcu.fragments.cabinet.donate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentDonatesBinding;
import com.d2.pcu.listeners.OnBackButtonClickListener;

public class DonateFragment extends Fragment {

    private FragmentDonatesBinding binding;
    private DonateViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnDonatesClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_donates, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(DonateViewModel.class);
        viewModel.setListener(listener);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (OnDonatesClickListener) context;
        onBackButtonClickListener = (OnBackButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
        onBackButtonClickListener = null;
    }
}
