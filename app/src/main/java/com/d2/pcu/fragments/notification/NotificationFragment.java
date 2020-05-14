package com.d2.pcu.fragments.notification;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentNotificationBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.utils.Constants;

public class NotificationFragment extends BaseFragment {

    private FragmentNotificationBinding binding;
    private NotificationViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(NotificationViewModel.class);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

        binding.setModel(viewModel);

        if (!Constants.NOTIFICATION_ENABLED) return;

        viewModel.enableLoading();
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.getData());

        adapter = new NotificationAdapter().setOnItemClickListener(
                (position) -> viewModel.setSelectedItem(position)
        );

        binding.notificationsRv.setAdapter(adapter);
        viewModel.getNotificationLiveData().observe(getViewLifecycleOwner(), (items) -> {
            binding.swipeRefresh.setRefreshing(false);
            adapter.setItems(items);
            viewModel.disableLoading();
        });

        viewModel.getData();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
        onLoadingStateChangedListener = null;
    }
}
