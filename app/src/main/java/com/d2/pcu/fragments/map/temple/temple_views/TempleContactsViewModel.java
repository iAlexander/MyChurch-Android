package com.d2.pcu.fragments.map.temple.temple_views;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnBackButtonClickListener;

public class TempleContactsViewModel extends ViewModel {

    private static final String TAG = TempleContactsViewModel.class.getSimpleName();

    private OnBackButtonClickListener onBackButtonClickListener;

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public void onGetDirectionsClick(View view) {
        Log.d(TAG, "onGetDirectionsClick: ");
    }

    public void onBackButtonPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
