package com.d2.pcu.fragments.cabinet;

import android.view.View;

import com.d2.pcu.R;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.fragments.cabinet.donate.OnDonatesClickListener;

public class CabinetViewModel extends BaseViewModel {

    private OnCabinetButtonsClickListener listener;
    private OnDonatesClickListener donatesClickListener;

    public CabinetViewModel() {
    }

    public void setListener(OnCabinetButtonsClickListener listener) {
        this.listener = listener;
    }

    public void setDonatesClickListener(OnDonatesClickListener donatesClickListener) {
        this.donatesClickListener = donatesClickListener;
    }

    public void onProfileClick(View view) {

        if (listener != null) {
            listener.onProfileClick();
        }
    }

    public void onSupportClick(View view) {
        if (listener != null) {
            listener.onSupportClick();
        }
    }

    public void onDonateClick(View view) {
        if (listener != null) {
            listener.onDonateClick();
        }
    }

    public void onTechSupportClick(View view) {
        if (listener != null) {
            listener.onTechSupportClick();
        }
    }
}
