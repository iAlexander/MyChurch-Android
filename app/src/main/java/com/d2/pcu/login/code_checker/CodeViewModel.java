package com.d2.pcu.login.code_checker;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.login.phone_checker.OnPhoneSent;


public class CodeViewModel extends ViewModel implements TextWatcher {

    private static final String TAG = CodeViewModel.class.getSimpleName();
    private ResendCodeTimer resendCodeTimer;

    private MutableLiveData<Boolean> btnEnable = new MutableLiveData<>(true);

    public CodeViewModel() {
        resendCodeTimer = new ResendCodeTimer();
        resendCodeTimer.startTimer();
    }

    public ResendCodeTimer getResendCodeTimer() {
        return resendCodeTimer;
    }

    private MutableLiveData<String> codeChannel = new MutableLiveData<>();
    private ObservableField<String> code = new ObservableField<>("");

    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnBackButtonClickListener onBackButtonClickListener;

    public void setOnLoadingStateChangeListener(OnLoadingStateChangedListener onLoadingStateChangeListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangeListener;
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    private OnCodeSent listener;
    private OnPhoneSent phoneListener;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    MutableLiveData<String> getCodeChannel() {
        return codeChannel;
    }

    public boolean getBtnEnable() {
        return btnEnable.getValue();
    }

    LiveData<Boolean> isBtnEnable() {
        return btnEnable;
    }

    public void apply(View view) {
        Log.i(TAG, code.get());
        listener.onCodeSent(code.get());
        onLoadingStateChangedListener.enableLoading(true);
    }

    public void resendCode(View view) {
        phoneListener.onPhoneSent(phone);
        resendCodeTimer.startTimer();
        btnEnable.setValue(true);
    }

    void enableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(true);
        }
    }

    void disableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        codeChannel.setValue(s.toString());
    }

    public void setListener(OnCodeSent listener) {
        this.listener = listener;
    }

    void setPhoneListener(OnPhoneSent phoneListener) {
        this.phoneListener = phoneListener;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
