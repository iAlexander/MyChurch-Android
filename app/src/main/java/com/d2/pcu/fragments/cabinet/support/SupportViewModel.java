package com.d2.pcu.fragments.cabinet.support;

import android.view.View;

import com.d2.pcu.fragments.BaseViewModel;

public class SupportViewModel extends BaseViewModel {

    private OnChatClickListener onChatClickListener;

    void setOnChatClickListener(OnChatClickListener onChatClickListener) {
        this.onChatClickListener = onChatClickListener;
    }

    public void onChatClick(View view) {
        if (onChatClickListener != null) {
            onChatClickListener.onChatClick(view.getId());
        }
    }
}
