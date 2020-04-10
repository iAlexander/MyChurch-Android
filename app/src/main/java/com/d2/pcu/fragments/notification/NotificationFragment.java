package com.d2.pcu.fragments.notification;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentNotificationBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnBackButtonClickListener;

public class NotificationFragment extends BaseFragment {

    private FragmentNotificationBinding binding;
    private NotificationViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(NotificationViewModel.class);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
    }
}
