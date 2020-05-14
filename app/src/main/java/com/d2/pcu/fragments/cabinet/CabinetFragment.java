package com.d2.pcu.fragments.cabinet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.databinding.ProfileFragmentBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnNotificationClickListener;

public class CabinetFragment extends BaseFragment {

    private ProfileFragmentBinding binding;
    private CabinetViewModel viewModel;

    private OnCabinetButtonsClickListener listener;

    public static CabinetFragment newInstance() {
        return new CabinetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CabinetViewModel.class);
        viewModel.setListener(listener);
        setViewModelListeners(viewModel);

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (OnCabinetButtonsClickListener) context;
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }
}
