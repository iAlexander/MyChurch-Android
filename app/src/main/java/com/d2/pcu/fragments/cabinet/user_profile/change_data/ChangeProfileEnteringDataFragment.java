package com.d2.pcu.fragments.cabinet.user_profile.change_data;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentUserEnteringDataChangeBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.cabinet.user_profile.ProfileMenuViewModel;
import com.d2.pcu.listeners.InfoDialogListener;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnNotificationClickListener;
import com.google.android.material.textfield.TextInputLayout;

public class ChangeProfileEnteringDataFragment extends BaseFragment {

    private FragmentUserEnteringDataChangeBinding binding;
    private ChangeProfileEnteringDataViewModel viewModel;

    private InfoDialogListener infoDialogListener;

    private ProfileMenuViewModel.ChangeDataType changeDataType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            changeDataType = (ProfileMenuViewModel.ChangeDataType) getArguments().get("changeDataType");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserEnteringDataChangeBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (changeDataType == ProfileMenuViewModel.ChangeDataType.EMAIL) {
            binding.changeEnteringDataTopbarTitleTv.setText(getString(R.string.change_email));

            binding.titleOldValueTv.setVisibility(View.GONE);
            binding.changeEnteringDataOldPasswordInputL.setVisibility(View.GONE);
            binding.changeEnteringDataOldPasswordEt.setVisibility(View.GONE);

            binding.titleNewValueTv.setText(getString(R.string.new_email_title));

            binding.changeEnteringDataOldPasswordInputL.setEndIconMode(TextInputLayout.END_ICON_NONE);
            binding.changeEnteringDataOldPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            binding.changeEnteringDataNewPasswordInputL.setEndIconMode(TextInputLayout.END_ICON_NONE);
            binding.changeEnteringDataNewPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ChangeProfileEnteringDataViewModel.class);
        viewModel.setInfoDialogListener(infoDialogListener);
        setViewModelListeners(viewModel);
        viewModel.setChangeDataType(changeDataType);

        binding.setModel(viewModel);

        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count ->
                binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        infoDialogListener = (InfoDialogListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        infoDialogListener = null;
    }
}
