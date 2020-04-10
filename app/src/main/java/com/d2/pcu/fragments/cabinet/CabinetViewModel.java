package com.d2.pcu.fragments.cabinet;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;

public class CabinetViewModel extends ViewModel {

    private Repository repository;
    private OnCabinetButtonsClickListener listener;

    public CabinetViewModel() {
        repository = App.getInstance().getRepositoryInstance();
    }

    public void setListener(OnCabinetButtonsClickListener listener) {
        this.listener = listener;
    }

    public void onProfileClick(View view) {
//        if (repository.getAuthState()) {
            // TODO: 2020-02-21
//        } else {
            // TODO: 2020-02-21
//        }

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
}
