package com.d2.pcu.fragments.cabinet.support.tech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentSupportTechBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.BaseViewModel;

public class TechSupportFragment extends BaseFragment {

    private FragmentSupportTechBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSupportTechBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaseViewModel viewModel = new ViewModelProvider(this).get(BaseViewModel.class);
        setViewModelListeners(viewModel);

        binding.setModel(viewModel);

        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count ->
                binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active));
    }
}
