package com.d2.pcu.fragments.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.databinding.FragmentReadNotificationBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.utils.Constants;

public class NotificationReadFragment extends BaseFragment {

    private FragmentReadNotificationBinding binding;
    private NotificationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReadNotificationBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(NotificationViewModel.class);

        setViewModelListeners(viewModel);

        binding.setModel(viewModel);

        if (!Constants.NOTIFICATION_ENABLED) return;

        viewModel.enableLoading();

        viewModel.getNotificationItemLiveData().observe(getViewLifecycleOwner(), item -> {
            viewModel.disableLoading();
            binding.setItem(item);
        });

//        viewModel.getItemData();
    }
}
