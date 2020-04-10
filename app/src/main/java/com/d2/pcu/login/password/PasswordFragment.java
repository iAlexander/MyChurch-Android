package com.d2.pcu.login.password;

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
import com.d2.pcu.databinding.FragmentPasswordBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

public class PasswordFragment extends BaseFragment {

    private FragmentPasswordBinding binding;
    private PasswordViewModel viewModel;

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnPasswordSetListener onPasswordSetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
        viewModel.setOnPasswordSetListener(onPasswordSetListener);
        viewModel.disableLoading();

        binding.passwordView.post(new Runnable() {
            @Override
            public void run() {
                binding.passwordView.requestFocus();
            }
        });
        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
        onPasswordSetListener = (OnPasswordSetListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
        onLoadingStateChangedListener = null;
        onPasswordSetListener = null;
    }
}
