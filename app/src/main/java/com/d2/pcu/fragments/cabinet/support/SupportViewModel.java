package com.d2.pcu.fragments.cabinet.support;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnBackButtonClickListener;

public class SupportViewModel extends ViewModel {

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnChatClickListener onChatClickListener;

    void setOnBackPressedClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setOnChatClickListener(OnChatClickListener onChatClickListener) {
        this.onChatClickListener = onChatClickListener;
    }

    public void onChatClick(View view) {
        if (onChatClickListener != null) {
           onChatClickListener.onChatClick(view.getId());
        }
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
