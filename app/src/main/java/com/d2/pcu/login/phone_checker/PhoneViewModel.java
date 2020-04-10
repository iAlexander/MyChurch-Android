package com.d2.pcu.login.phone_checker;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

public class PhoneViewModel extends ViewModel implements TextWatcher {

    private static final String TAG = PhoneViewModel.class.getSimpleName();

    private MutableLiveData<String> phoneChannel = new MutableLiveData<String>();

    private ObservableField<String> phone = new ObservableField<String>();

    private OnPhoneSent listener;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private OnBackButtonClickListener onBackButtonClickListener;

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public MutableLiveData<String> getPhoneChannel() {
        return phoneChannel;
    }

    public void setOnLoadingStateChangeListener(OnLoadingStateChangedListener onLoadingStateChangeListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangeListener;
    }

    public void apply(View view) {

//        String brand = Build.BRAND;
//        String manufacturer = Build.MANUFACTURER;
//        String model = Build.MODEL;
//        int version = Build.VERSION.SDK_INT;
//        String versionRelease = Build.VERSION.RELEASE;

        String phoneWithCode = "380" + phone.get();
        Log.i(TAG, phoneWithCode);

        onLoadingStateChangedListener.enableLoading(true);
        listener.onPhoneSent(phoneWithCode);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        phoneChannel.setValue(s.toString());
    }

    public void setListener(OnPhoneSent listener) {
        this.listener = listener;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
