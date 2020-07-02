package com.d2.pcu;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.d2.pcu.data.Repository;
import com.d2.pcu.data.db.AppDatabase;
import com.d2.pcu.services.NotificationHelper;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.DownloadUtil;
import com.d2.pcu.utils.exo.ExoHelper;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.yariksoffice.lingver.Lingver;

import timber.log.Timber;

public class App extends Application {

    private static App instance;
    private Repository repository;
    private AppDatabase database;
    private NotificationHelper notificationHelper;

    private String firebaseToken;

    private ExoHelper exoHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG || BuildConfig.isDebugEnabled) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        instance = this;
        repository = new Repository(this);
        database = Room.databaseBuilder(this, AppDatabase.class, "pcu_database")
                .fallbackToDestructiveMigration()
                .build();

        if (Constants.AUDIO_ENABLED) {
            DownloadUtil.init(this);
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.w(task.getException(), "getInstanceId failed");
                    } else if (task.getResult() != null) {
                        // Get new Instance ID token
                        firebaseToken = task.getResult().getToken();
                        if (!repository.getCredentials(Constants.PUSH_TOKEN).equals(firebaseToken)) {
                            repository.updatePushToken(firebaseToken);
                        }
                        Timber.d("Firebase Message Token: %s", firebaseToken);
                    }
                });

        Lingver.init(this, "uk");
    }

    public static App getInstance() {
        return instance;
    }

    public Repository getRepositoryInstance() {
        return repository;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public String getFirebaseToken() {
        return firebaseToken == null ? "" : firebaseToken;
    }

    public ExoHelper getExoHelper() {
        if (exoHelper == null) {
            exoHelper = new ExoHelper(this);
        }
        return exoHelper;
    }

    public NotificationHelper getNotificationHelper(){
        if(notificationHelper==null){
            notificationHelper = new NotificationHelper(getRepositoryInstance());
            notificationHelper.syncNotification();
        }
        return notificationHelper;
    }

    public void logEvent(String name, String  type){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(name, type);

        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private class CrashReportingTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, @NonNull String message, Throwable throwable) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            if (throwable == null) {
                FirebaseCrashlytics.getInstance().log(message);
            } else {
                FirebaseCrashlytics.getInstance().recordException(throwable);
            }
        }
    }
}
