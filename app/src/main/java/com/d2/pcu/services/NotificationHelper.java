package com.d2.pcu.services;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;

import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.utils.Constants;

import java.util.List;

import timber.log.Timber;

public final class NotificationHelper {
    private boolean signIn = false;
    private final Repository repository;
    private final LiveData<Integer> unreadCount;

    public NotificationHelper(Repository repository) {
        this.repository = repository;
        unreadCount = repository.getUnreadNotificationCount();
    }

    public void syncNotification() {
        if (!TextUtils.isEmpty(repository.getCredentials(Constants.ACCESS_TOKEN))) {
            signIn = true;
        }
    }

    public boolean isSignIn() {
        return signIn;
    }

    public LiveData<Integer> getUnreadCount() {
        return unreadCount;
    }
}
