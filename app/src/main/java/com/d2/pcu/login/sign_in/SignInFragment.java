package com.d2.pcu.login.sign_in;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.databinding.FragmentSignInBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.login.OnLoginError;
import com.d2.pcu.login.SignInOnClickListener;

public class SignInFragment extends BaseFragment {

    private FragmentSignInBinding binding;
    private SignInViewModel viewModel;

    private SignInOnClickListener signInOnClickListener;
    private OnLoginError onLoginError;

    private boolean email = false;
    private boolean pass = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signInEmailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 2) {
                    email = false;
                    checkBtnState();
                    return;
                }

                email = true;
                checkBtnState();
            }
        });

        binding.signInPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < 1) {
                    pass = false;
                    checkBtnState();
                    return;
                }

                pass = true;
                checkBtnState();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(SignInViewModel.class);
        setViewModelListeners(viewModel);
        viewModel.setSignInOnClickListener(signInOnClickListener);
        viewModel.setOnLoginError(onLoginError);

        binding.setModel(viewModel);

        checkBtnState();
    }

    private void checkBtnState() {
        if (email && pass) {
            binding.bApply.setEnabled(true);
        } else {
            binding.bApply.setEnabled(false);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        signInOnClickListener = (SignInOnClickListener) context;
        onLoginError = (OnLoginError) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        signInOnClickListener = null;
        onLoginError = null;
    }
}
