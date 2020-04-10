package com.d2.pcu.login.password;

import android.view.View;

import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;

public class PasswordViewModel extends BaseViewModel {

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnPasswordSetListener onPasswordSetListener;

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public void setOnPasswordSetListener(OnPasswordSetListener onPasswordSetListener) {
        this.onPasswordSetListener = onPasswordSetListener;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void apply(View view) {
        if (password.length() == 6) {
            if (onPasswordSetListener != null) {
                onPasswordSetListener.setPassword();
            }
        }
    }
}
