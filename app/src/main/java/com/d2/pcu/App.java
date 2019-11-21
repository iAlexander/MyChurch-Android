package com.d2.pcu;

import android.app.Application;

import com.d2.pcu.data.Repository;

public class App extends Application {

    private static App instance;
    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        repository = new Repository();
    }

    public static App getInstance() {
        return instance;
    }

    public Repository getRepositoryInstance() {
        return repository;
    }
}
