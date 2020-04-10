package com.d2.pcu.login.sign_in;

import android.view.View;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.login.OnLoginError;
import com.d2.pcu.login.SignInOnClickListener;
import com.d2.pcu.login.sign_up.UserProfileViewModel;

public class SignInViewModel extends BaseViewModel {

    private Repository repository;

    private String email = "";
    private String password = "";

    private OnBackButtonClickListener onBackButtonClickListener;
    private SignInOnClickListener signInOnClickListener;
    private OnLoginError onLoginError;

    public SignInViewModel() {
        repository = App.getInstance().getRepositoryInstance();
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setSignInOnClickListener(SignInOnClickListener signInOnClickListener) {
        this.signInOnClickListener = signInOnClickListener;
    }

    void setOnLoginError(OnLoginError onLoginError) {
        this.onLoginError = onLoginError;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void signIn(View view) {
        if (signInOnClickListener != null) {
            if (email.isEmpty()) {
                // TODO: 31.03.2020  Empty email sign in
            }
            if (password.isEmpty()) {
                // TODO: 31.03.2020 Empty password sign in
            }

            repository.signIn(email, password, new UserProfileViewModel.OnRequestResult() {
                @Override
                public void onSuccess() {
                    signInOnClickListener.onEnterClick();
                }
            });
        }
    }

    public void forgotPass(View view) {
        if (signInOnClickListener != null) {

            if (onLoginError != null) {

                if (email.isEmpty()) {
                    onLoginError.onError("Заповніть адресу пошти і натисніть кнопку");
                    return;
                }

                repository.forgotPass(email, new UserProfileViewModel.OnRequestResult() {
                    @Override
                    public void onSuccess() {
                        signInOnClickListener.onForgotPassClick();
                    }
                });
            }
        }
    }

    public void createAcc(View view) {
        if (signInOnClickListener != null) {
            signInOnClickListener.onCreateAccCLick();
        }
    }
}
