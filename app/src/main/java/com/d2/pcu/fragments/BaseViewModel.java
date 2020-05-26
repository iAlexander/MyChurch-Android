package com.d2.pcu.fragments;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.listeners.OnNotificationClickListener;
import com.d2.pcu.services.NotificationHelper;

import java.util.List;

public class BaseViewModel extends ViewModel {

    private OnNotificationClickListener onNotificationClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnBackButtonClickListener onBackButtonClickListener;
    private NotificationHelper notificationHelper;

    public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
    }

    public void enableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(true);
        }
    }

    public void disableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
    }

    public void setOnNotificationClickListener(OnNotificationClickListener onNotificationClickListener) {
        this.onNotificationClickListener = onNotificationClickListener;
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void onNotificationClick(View view) {
        if (onNotificationClickListener != null) {
            onNotificationClickListener.onNotificationClick();
        }
    }

    public boolean shouldShowNotification() {
        if(notificationHelper==null){
            notificationHelper = App.getInstance().getNotificationHelper();
        }
        return notificationHelper.isSignIn();
    }

    public LiveData<Integer> shouldShowAsUnreadNotification(){
        if(notificationHelper==null){
            notificationHelper = App.getInstance().getNotificationHelper();
        }

        return notificationHelper.getUnreadCount();
    }
}
