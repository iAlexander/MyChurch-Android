package com.d2.pcu.services;

import android.text.TextUtils;

import com.d2.pcu.data.Repository;
import com.d2.pcu.utils.Constants;

public final class NotificationHelper {
    private boolean signIn = false;
    private final Repository repository;

    public NotificationHelper(Repository repository) {
        this.repository = repository;
    }

    public void syncNotification() {
        if (!TextUtils.isEmpty(repository.getCredentials(Constants.ACCESS_TOKEN))) {
            signIn = true;
        }

    }

    public boolean isSignIn() {
        return signIn;
    }

}
