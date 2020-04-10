package com.d2.pcu.login.user_type;

import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.R;
import com.d2.pcu.listeners.OnBackButtonClickListener;


public class UserTypeViewModel extends ViewModel {
    private static final String TAG = UserTypeViewModel.class.getSimpleName();

    private OnBackButtonClickListener onBackButtonClickListener;

    private OnUserTypeSelected listener;

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public void selectType(View view) {
        switch (view.getId()){
            case R.id.reg_believer : {
                userTypeSelected(UserType.BELIEVER);
                break;
            }
            case R.id.reg_clergy : {
                userTypeSelected(UserType.CLERGY);
                break;
            }
            case R.id.reg_bishop : {
                userTypeSelected(UserType.BISHOP);
                break;
            }
        }
    }

    public void setListener(OnUserTypeSelected listener) {
        this.listener = listener;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    private void userTypeSelected(UserType userType){
        if (listener != null) {
            listener.onUserTypeSelected(userType);
        }
    }
}
