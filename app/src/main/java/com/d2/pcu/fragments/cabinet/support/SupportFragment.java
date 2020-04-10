package com.d2.pcu.fragments.cabinet.support;

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
import com.d2.pcu.databinding.FragmentSupportChatsBinding;
import com.d2.pcu.listeners.OnBackButtonClickListener;

public class SupportFragment extends Fragment {

    private FragmentSupportChatsBinding binding;
    private SupportViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnChatClickListener onChatClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_support_chats, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SupportViewModel.class);
        viewModel.setOnBackPressedClickListener(onBackButtonClickListener);
        viewModel.setOnChatClickListener(onChatClickListener);


        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onChatClickListener = (OnChatClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        onBackButtonClickListener = null;
        onChatClickListener = null;
    }
}
