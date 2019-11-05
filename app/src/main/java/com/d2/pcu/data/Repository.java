package com.d2.pcu.data;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;

import com.d2.pcu.ui.error.OnError;

public class Repository implements LifecycleObserver, LifecycleOwner {

    private static final String TAG = Repository.class.getSimpleName();

    private LifecycleRegistry lifecycleRegistry;

    private NetLoader netLoader;
    private Transport channels;
    private SharedPreferences sharedPreferences;

    private OnError onError;
    public void setOnErrorListener(OnError onError) {
        this.onError = onError;
    }

    public Repository() {
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
        channels = new Transport();

        netLoader = new NetLoader();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        getLifecycle().addObserver(netLoader);

        Log.i(TAG, "Created");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    public Transport getTransport() {
        return channels;
    }
}
