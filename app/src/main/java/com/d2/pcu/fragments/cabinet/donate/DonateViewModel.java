package com.d2.pcu.fragments.cabinet.donate;

import android.view.View;

import com.d2.pcu.fragments.BaseViewModel;

public class DonateViewModel extends BaseViewModel {

    private OnDonatesClickListener listener;

    void setListener(OnDonatesClickListener listener) {
        this.listener = listener;
    }

    public void onDonateClick(View view) {
        if (listener != null) {
            listener.onDonateServiceClick(view.getId());
        }
    }

}
