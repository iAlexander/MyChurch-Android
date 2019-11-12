package com.d2.pcu.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.ui.error.HTTPCode;
import com.d2.pcu.ui.error.HTTPException;
import com.d2.pcu.ui.error.OnHTTPResult;
import com.d2.pcu.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
                .baseUrl(Constants.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(AppAPI.class);

        handlerThread = new HandlerThread("netLoader");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());

        Log.i(TAG, "Created");
    }

    void getBaseTemplesInfo(final double lt, final double lg, final int radius, final OnHTTPResult result) {
        handler.post(() -> api.getBaseTemplesInfo(lt, lg, radius).enqueue(new Callback<BaseTempleResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseTempleResponse> call, @NonNull Response<BaseTempleResponse> response) {
                int resCode = response.code();

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<BaseTempleResponse> call, @NonNull Throwable t) {
                result.onFail(t);
            }
        }));
    }
}
