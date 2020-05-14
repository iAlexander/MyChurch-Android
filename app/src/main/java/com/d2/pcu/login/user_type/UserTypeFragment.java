package com.d2.pcu.login.user_type;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.databinding.FragmentUserTypeBinding;
import com.d2.pcu.fragments.BaseFragment;

public class UserTypeFragment extends BaseFragment {

    private UserTypeViewModel viewModel;
    private FragmentUserTypeBinding binding;

    private OnUserTypeSelected onUserTypeSelected;

    public static UserTypeFragment newInstance() {
        return new UserTypeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserTypeBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(UserTypeViewModel.class);
        viewModel.setListener(onUserTypeSelected);
        setViewModelListeners(viewModel);

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onUserTypeSelected = (OnUserTypeSelected) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onUserTypeSelected = null;
    }
}
