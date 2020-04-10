package com.d2.pcu.fragments.cabinet.donate;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnBackButtonClickListener;

public class DonateViewModel extends ViewModel {

    private OnDonatesClickListener listener;
    private OnBackButtonClickListener onBackButtonClickListener;

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setListener(OnDonatesClickListener listener) {
        this.listener = listener;
    }

    public void onDonateClick(View view) {
        if (listener != null) {
            listener.onDonateServiceClick(view.getId());
        }
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
