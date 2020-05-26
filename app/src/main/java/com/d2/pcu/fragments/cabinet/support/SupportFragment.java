package com.d2.pcu.fragments.cabinet.support;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentSupportChatsBinding;
import com.d2.pcu.fragments.BaseFragment;

public class SupportFragment extends BaseFragment {

    private FragmentSupportChatsBinding binding;
    private SupportViewModel viewModel;

    private OnChatClickListener onChatClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSupportChatsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SupportViewModel.class);
        setViewModelListeners(viewModel);
        viewModel.setOnChatClickListener(onChatClickListener);

        binding.setModel(viewModel);

        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count ->
                binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onChatClickListener = (OnChatClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onChatClickListener = null;
    }
}
