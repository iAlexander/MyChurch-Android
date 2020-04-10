package com.d2.pcu.fragments;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnLoadingStateChangedListener;

public class BaseViewModel extends ViewModel {

    private OnLoadingStateChangedListener onLoadingStateChangedListener;

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
}
