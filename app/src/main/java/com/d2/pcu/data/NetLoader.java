package com.d2.pcu.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetLoader implements LifecycleObserver {

    private static final String TAG = NetLoader.class.getSimpleName();
    private static final String RESPONSE_STATE_SUCCESS = "SUCCESS";

    private Gson gson;
    private OkHttpClient httpClient;
    private Retrofit retrofit;

    private AppAPI api;

    private HandlerThread handlerThread;
    private Handler handler;

    public NetLoader() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        gson = new GsonBuilder().create();

        httpClient = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .cache(null)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(AppAPI.class);

        handlerThread = new HandlerThread("netLoader");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());

        Log.i(TAG, "Created");
    }
}
