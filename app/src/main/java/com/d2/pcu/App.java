package com.d2.pcu;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.os.EnvironmentCompat;
import androidx.room.Room;

import com.d2.pcu.data.Repository;
import com.d2.pcu.data.db.AppDatabase;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.yariksoffice.lingver.Lingver;

import java.io.File;
import java.util.Locale;

import timber.log.Timber;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    private static App instance;
    private Repository repository;

    // A download cache should not evict media, so should use a NoopCacheEvictor.
    private SimpleCache downloadCache;

    // Create the download manager.
    private DownloadManager downloadManager;

    private AppDatabase database;

    private File downloadDirectory;

    private String firebaseToken;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        repository = new Repository(this);
        database = Room.databaseBuilder(this, AppDatabase.class, "pcu_database").build();

        Timber.plant(new Timber.DebugTree());

        downloadDirectory = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "prays");

        if (!downloadDirectory.exists()) {
            downloadDirectory.mkdir();
        }

        ExoDatabaseProvider databaseProvider = new ExoDatabaseProvider(this);

        // Create a factory for reading the data from the network.
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(this, getString(R.string.app_name))
        );

        downloadCache = new SimpleCache(
                downloadDirectory,
                new NoOpCacheEvictor(),
                databaseProvider);

        downloadManager = new DownloadManager(
                this,
                databaseProvider,
                downloadCache,
                dataSourceFactory);
        downloadManager.setMaxParallelDownloads(2);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        firebaseToken = task.getResult().getToken();
                        android.util.Log.d(TAG, "Firebase Message Token -> " + firebaseToken);
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

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public SimpleCache getDownloadCache() {
        return downloadCache;
    }

    public String getFirebaseToken() {
        return firebaseToken == null ? "" : firebaseToken;
    }

    public File getDownloadDirectory() {
        return downloadDirectory;
    }
}
