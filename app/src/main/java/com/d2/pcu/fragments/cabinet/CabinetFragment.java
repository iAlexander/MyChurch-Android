package com.d2.pcu.fragments.cabinet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.model.profile.UserState;
import com.d2.pcu.databinding.ProfileFragmentBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.cabinet.donate.OnDonatesClickListener;

public class CabinetFragment extends BaseFragment {

    private ProfileFragmentBinding binding;
    private CabinetViewModel viewModel;

    private OnCabinetButtonsClickListener listener;
    private OnDonatesClickListener donateListener;

    public static CabinetFragment newInstance() {
        return new CabinetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CabinetViewModel.class);
        viewModel.setListener(listener);
        viewModel.setDonatesClickListener(donateListener);
        setViewModelListeners(viewModel);
        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count -> {
            binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active);
        });

        binding.setModel(viewModel);

        initLoginButton();
    }

    private void initLoginButton() {
        if (App.getInstance().getRepositoryInstance().getAuthState() == UserState.AUTHENTICATED
                || App.getInstance().getRepositoryInstance().getAuthState() == UserState.SIGNED_UP) {
            binding.profileBtn.setText(R.string.auth_authenticated);
        } else {
            binding.profileBtn.setText(R.string.auth_login);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (OnCabinetButtonsClickListener) context;
        this.donateListener = (OnDonatesClickListener) context;
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }
}
