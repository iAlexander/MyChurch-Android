package com.d2.pcu.fragments.cabinet.user_profile.subscription;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.d2.pcu.R;
import com.d2.pcu.data.model.profile.SubscriptionStatus;
import com.d2.pcu.databinding.FragmentSubscriptionBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.cabinet.donate.OnDonatesClickListener;
import com.d2.pcu.ui.view.ClearErrorTextWatcher;
import com.d2.pcu.ui.view.DigitsInputFilter;

public class SubscriptionFragment extends BaseFragment {

    private FragmentSubscriptionBinding binding;
    private SubscriptionViewModel viewModel;
    private ClearErrorTextWatcher amountWatcher;
    private OnDonatesClickListener listener;
    private PaymentsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSubscriptionBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);
        viewModel.setListener(listener);
        setViewModelListeners(viewModel);

        binding.setModel(viewModel);

        binding.contactsExpandable.setListener(expanded -> {
            if (expanded) {
                binding.flSubscribe.setVisibility(View.GONE);
                binding.flUnSubscribe.setVisibility(View.GONE);
            } else {
                binding.flSubscribe.setVisibility(View.VISIBLE);
                binding.flUnSubscribe.setVisibility(View.VISIBLE);
            }
        });

        binding.etChargeAmount.setFilters(new InputFilter[]{new DigitsInputFilter(8, 2, Float.MAX_VALUE)});


        binding.btnSubscribe.setOnClickListener(v -> {

            if (TextUtils.isEmpty(binding.etChargeAmount.getEditableText().toString())) {
                showAmountError();
            } else {
                float value = Float.parseFloat(binding.etChargeAmount.getEditableText().toString());
                if (value < 0.01) showAmountError();
                else viewModel.subscribe(value);
            }
        });

        binding.btnUnSubscribe.setOnClickListener(v -> viewModel.unSubscribe());

        amountWatcher = new ClearErrorTextWatcher(binding.tiChargeAmount);
        binding.etChargeAmount.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                amountWatcher.startTrack();
            } else {
                amountWatcher.stopTrack();
            }
        });

        adapter = new PaymentsAdapter();

        binding.rvHistoryItems.setAdapter(adapter);

        subscribeObservers();
    }

    private void showAmountError() {
        binding.tiChargeAmount.setError(getString(R.string.error_wrong_payment_value));
        binding.contactsExpandable.collapse();
    }

    private void subscribeObservers() {
        viewModel.shouldShowAsUnreadNotification().removeObservers(getViewLifecycleOwner());
        viewModel.shouldShowAsUnreadNotification().observe(getViewLifecycleOwner(), count ->
                binding.ivNotificationBell.setImageResource(count == 0 ? R.drawable.ic_notifications_none : R.drawable.ic_notifications_active));

        viewModel.getUserProfileLiveData().removeObservers(getViewLifecycleOwner());
        viewModel.getUserProfileLiveData().observe(getViewLifecycleOwner(), userProfile -> {
                    if (userProfile != null) {
                        switch (userProfile.getSubscriptionStatus()) {
                            default:
                            case SubscriptionStatus.NOT_SUBSCRIBED: {
                                binding.btnUnSubscribe.setVisibility(View.GONE);
                                binding.btnSubscribe.setVisibility(View.VISIBLE);
                                binding.llSubscribed.setVisibility(View.GONE);
                                binding.llUnSubscribed.setVisibility(View.VISIBLE);
                                break;
                            }
                            case SubscriptionStatus.SUBSCRIBED: {
                                binding.btnSubscribe.setVisibility(View.GONE);
                                binding.btnUnSubscribe.setVisibility(View.VISIBLE);
                                binding.llUnSubscribed.setVisibility(View.GONE);
                                binding.llSubscribed.setVisibility(View.VISIBLE);
                                break;
                            }
                        }
                    }
                }
        );

        viewModel.getPaymentHistory().removeObservers(getViewLifecycleOwner());
        viewModel.getPaymentHistory().observe(getViewLifecycleOwner(), list -> {
            adapter.setItems(list);
            viewModel.disableLoading();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (OnDonatesClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
