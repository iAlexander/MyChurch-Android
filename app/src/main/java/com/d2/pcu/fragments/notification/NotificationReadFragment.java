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
    private int selectedId = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedId = bundle.getInt(Constants.PUSH_NOTIFICATION_ID, 0);
        }
        super.onCreate(savedInstanceState);
    }

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
        if (selectedId != 0) {
            viewModel.setSelectedId(selectedId);
            selectedId = 0;
        }

        setViewModelListeners(viewModel);

        binding.setModel(viewModel);

        if (!Constants.NOTIFICATION_ENABLED) return;

        viewModel.enableLoading();

        viewModel.getNotificationItemLiveData().observe(getViewLifecycleOwner(), item -> {
            viewModel.disableLoading();
            binding.setItem(item);
        });
    }
}
