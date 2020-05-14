package com.d2.pcu.login.user_type;

import android.view.View;

import com.d2.pcu.R;
import com.d2.pcu.fragments.BaseViewModel;


public class UserTypeViewModel extends BaseViewModel {

    private OnUserTypeSelected listener;

    public void selectType(View view) {
        switch (view.getId()) {
            case R.id.reg_believer: {
                userTypeSelected(UserType.BELIEVER);
                break;
            }
            case R.id.reg_clergy: {
                userTypeSelected(UserType.CLERGY);
                break;
            }
            case R.id.reg_bishop: {
                userTypeSelected(UserType.BISHOP);
                break;
            }
        }
    }

    public void setListener(OnUserTypeSelected listener) {
        this.listener = listener;
    }

    private void userTypeSelected(UserType userType) {
        if (listener != null) {
            listener.onUserTypeSelected(userType);
        }
    }
}
