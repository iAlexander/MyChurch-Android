package com.d2.pcu.fragments.map.temple;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnBackButtonClickListener;

public class TempleViewModel extends ViewModel {

    private OnBackButtonClickListener onBackButtonClickListener;

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public void onBackButtonPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
