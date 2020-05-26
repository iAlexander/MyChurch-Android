package com.d2.pcu.fragments.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.d2.pcu.databinding.FragmentNotificationBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.utils.Constants;

public class NotificationFragment extends BaseFragment {

    private FragmentNotificationBinding binding;
    private NotificationViewModel viewModel;

    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater);

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
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.getData());

        adapter = new NotificationAdapter().setOnItemClickListener(
                (position) -> {
                    viewModel.setSelectedItem(position);
                    NavDirections action = NotificationFragmentDirections
                            .actionNotificationFragmentToNotificationReadFragment();

                    NavHostFragment.findNavController(this).navigate(action);
                }
        );

        binding.notificationsRv.setAdapter(adapter);
        viewModel.getNotificationLiveData().observe(getViewLifecycleOwner(), (items) -> {
            binding.swipeRefresh.setRefreshing(false);
            adapter.setItems(items);
            viewModel.disableLoading();
        });
        viewModel.getData();
    }
}
