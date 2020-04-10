package com.d2.pcu.login.code_checker;

import android.os.CountDownTimer;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


public class ResendCodeTimer extends BaseObservable {

    private String seconds;
    private boolean action;

    void startTimer() {
        setAction(false);
        setSeconds("30");

        new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                setSeconds(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                setAction(true);
            }
        }.start();
    }

    @Bindable
    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
        notifyPropertyChanged(androidx.databinding.library.baseAdapters.BR.seconds);
    }

    @Bindable
    public boolean getAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
        notifyPropertyChanged(BR.action);
    }
}
