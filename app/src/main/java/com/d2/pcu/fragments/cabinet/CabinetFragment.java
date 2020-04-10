package com.d2.pcu.fragments.cabinet;

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

import com.d2.pcu.R;
import com.d2.pcu.databinding.ProfileFragmentBinding;

public class CabinetFragment extends Fragment {

    private ProfileFragmentBinding binding;
    private CabinetViewModel viewModel;

    private OnCabinetButtonsClickListener listener;

    public static CabinetFragment newInstance() {
        return new CabinetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(CabinetViewModel.class);
        viewModel.setListener(listener);

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (OnCabinetButtonsClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
