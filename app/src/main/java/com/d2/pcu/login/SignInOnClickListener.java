package com.d2.pcu.login;

public interface SignInOnClickListener {

    void onForgotPassClick(String email);

    void onEnterClick();

    void onCreateAccCLick();

    void onSignedUp(boolean moderating);
}
