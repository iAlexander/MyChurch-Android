package com.d2.pcu;

import android.app.Application;

import androidx.room.Room;

import com.d2.pcu.data.Repository;
import com.d2.pcu.data.db.AppDatabase;
import com.d2.pcu.utils.DownloadUtil;
import com.google.firebase.iid.FirebaseInstanceId;
import com.yariksoffice.lingver.Lingver;

import timber.log.Timber;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    private static App instance;
    private Repository repository;
    private AppDatabase database;

    private String firebaseToken;
    private DownloadUtil downloadUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        repository = new Repository(this);
        database = Room.databaseBuilder(this, AppDatabase.class, "pcu_database").build();

        Timber.plant(new Timber.DebugTree());
        DownloadUtil.init(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.w(task.getException(), "getInstanceId failed");
                        return;
                    }

                    // Get new Instance ID token
                    firebaseToken = task.getResult().getToken();
                    Timber.d("Firebase Message Token: %s", firebaseToken);
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

}
