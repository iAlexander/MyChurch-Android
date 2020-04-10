package com.d2.pcu.login.user_type;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentUserTypeBinding;
import com.d2.pcu.listeners.OnBackButtonClickListener;

public class UserTypeFragment extends Fragment {

    private static final String TAG = UserTypeFragment.class.getSimpleName();

    private UserTypeViewModel viewModel;
    private FragmentUserTypeBinding binding;

    private OnUserTypeSelected onUserTypeSelected;
    private OnBackButtonClickListener onBackButtonClickListener;

    public static UserTypeFragment newInstance() {
        return new UserTypeFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_type, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(UserTypeViewModel.class);
        viewModel.setListener(onUserTypeSelected);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

        binding.setModel(viewModel);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onUserTypeSelected = (OnUserTypeSelected) context;
        onBackButtonClickListener = (OnBackButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onUserTypeSelected = null;
        onBackButtonClickListener = null;
    }
}
