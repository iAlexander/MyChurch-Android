package com.d2.pcu.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.model.profile.UserState;
import com.d2.pcu.listeners.InfoDialogListener;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.login.code_checker.OnErrorChannelCallback;
import com.d2.pcu.login.user_type.OnUserTypeSelected;
import com.d2.pcu.login.user_type.UserType;
import com.d2.pcu.ui.utils.OverlayView;
import com.d2.pcu.ui.utils.UIUtils;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.livedata_utils.SingleLiveEvent;


public class LoginActivity extends AppCompatActivity implements
        OnLoadingStateChangedListener, OnUserTypeSelected,
        OnErrorChannelCallback, OnBackButtonClickListener,
        SignInOnClickListener, OnLoginError, InfoDialogListener {

    private NavController navController;

    private UserType userType;
    private String phone;
    private String code;

    private OverlayView loadingView;

    //Error channel
    protected SingleLiveEvent<Boolean> errorChannel = new SingleLiveEvent<>();

    @Override
    public SingleLiveEvent<Boolean> getErrorChannel() {
        return errorChannel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        navController = Navigation.findNavController(this, R.id.nav_login_host_fragment);
        loadingView = findViewById(R.id.login_loading_view);

        App.getInstance().getRepositoryInstance().setOnLoginErrorListener(this);

//        channelsListen();
    }

//    private void channelsListen(){
//        getRepository().getTransport().getCheckPhoneChannel().observe(this, new Observer<CheckPhoneResponse>() {
//            @Override
//            public void onChanged(CheckPhoneResponse checkPhoneResponse) {
//                if (checkPhoneResponse.getState().equals("SUCCESS") && checkPhoneResponse.isExist()) {
//                    Bundle arg = new Bundle();
//                    arg.putString("phone", phone);
//                    errorChannel.setValue(false);
//                    navController.navigate(R.id.checkFragment, arg);
//                } else {
//                    errorChannel.setValue(true);
//                    onError(getString(R.string.phone_not_found), Constants.ERROR_TYPE_WRONG_PHONE_NUMBER);
//                }
//
//            }
//        });
//
//        getRepository().getTransport().getLoginChannel().observe(this, new Observer<LoginResponse>() {
//            @Override
//            public void onChanged(LoginResponse loginResponse) {
//                if (loginResponse.getState().equals("SUCCESS")) {
//                    getRepository().saveCredential(loginResponse.getUserData().getToken(), loginResponse.getUserData().getUserID());
//                    Log.i(TAG, "Login success");
//                    errorChannel.setValue(false);
//                    navController.navigate(R.id.mainActivity);
//                    finish();
//                } else {
//                    errorChannel.setValue(true);
//                }
//            }
//        });
//    }

    @Override
    public void onUserTypeSelected(UserType userType) {
        this.userType = userType;
        App.getInstance().getRepositoryInstance().saveUserType(userType);

        Bundle arg = new Bundle();
        arg.putString(Constants.USER_TYPE, userType.toString());

        navController.navigate(R.id.userProfileFragment, arg);
    }


    @Override
    public void onError(String message) {
        enableLoading(false);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void enableLoading(boolean enable) {
        loadingView.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackButtonPressed() {
        if (!navController.popBackStack()) {

            finish();
        }
    }

    @Override
    public void onForgotPassClick() {
        showInfoDialog(getString(R.string.forgot_pass_success_text), getString(R.string.next_btn_text));
    }

    @Override
    public void onEnterClick() {
        App.getInstance().getNotificationHelper().syncNotification();
        Toast.makeText(this, "Вхід виконано успішно", Toast.LENGTH_LONG).show();
        saveUserState(UserState.AUTHENTICATED);
        App.getInstance().getRepositoryInstance().getTransport().getStateSingleEvent().setValue(UserState.AUTHENTICATED);
        finish();
    }

    @Override
    public void onCreateAccCLick() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        navController.navigate(R.id.userTypeFragment);
    }

    @Override
    public void onSignedUp(boolean moderating) {
        App.getInstance().getNotificationHelper().syncNotification();
        Toast.makeText(this, "Реєстрація пройшла успішно", Toast.LENGTH_LONG).show();
        if (moderating) {
            App.getInstance().getRepositoryInstance().getTransport().getStateSingleEvent().setValue(UserState.MODERATING);
            saveUserState(UserState.MODERATING);
        } else {
            App.getInstance().getRepositoryInstance().getTransport().getStateSingleEvent().setValue(UserState.SIGNED_UP);
            saveUserState(UserState.SIGNED_UP);
        }
//        if (userType == UserType.BELIEVER) {
//            App.getInstance().getRepositoryInstance().getTransport().getStateSingleEvent().setValue(UserState.SIGNED_UP);
//            saveUserState(UserState.SIGNED_UP);
//        } else {
//            App.getInstance().getRepositoryInstance().getTransport().getStateSingleEvent().setValue(UserState.MODERATING);
//            saveUserState(UserState.MODERATING);
//        }
        finish();
    }

    private void saveUserState(UserState userState) {
        App.getInstance().getRepositoryInstance().setAuthState(userState);
    }

    @Override
    public void showInfoDialog(String msg, String btnTitle) {
        UIUtils.assembleModeratingDialog(this, msg, btnTitle).show();
    }

    @Override
    public void showInfoDialog(int msgId, int btnTitleId) {
        UIUtils.assembleModeratingDialog(this, getString(msgId), getString(btnTitleId));
    }
}
