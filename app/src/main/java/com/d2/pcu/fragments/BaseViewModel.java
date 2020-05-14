package com.d2.pcu.fragments;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.listeners.OnNotificationClickListener;

public class BaseViewModel extends ViewModel {

    private OnNotificationClickListener onNotificationClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnBackButtonClickListener onBackButtonClickListener;

    public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
    }

    public void enableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(true);
        }
    }

    public void disableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
    }

    public void setOnNotificationClickListener(OnNotificationClickListener onNotificationClickListener) {
        this.onNotificationClickListener = onNotificationClickListener;
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void onNotificationClick(View view) {
        if (onNotificationClickListener != null) {
            onNotificationClickListener.onNotificationClick();
        }
    }
}
