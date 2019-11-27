package com.d2.pcu.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.responses.calendar.EventResponse;
import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.data.responses.map.TemplesResponse;
import com.d2.pcu.ui.error.HTTPCode;
import com.d2.pcu.ui.error.HTTPException;
import com.d2.pcu.ui.error.OnHTTPResult;
import com.d2.pcu.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
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

    void getBaseTemplesInfo(final double lt, final double lg, final OnHTTPResult result) {
        handler.post(() -> api.getBaseTemplesInfo(lt, lg, 5000).enqueue(new Callback<BaseTempleResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseTempleResponse> call, @NonNull Response<BaseTempleResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getBaseTemplesInfo -> onResponseCode: " + resCode);

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

    void getTempleById(final int id, final OnHTTPResult result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                api.getTempleById(id).enqueue(new Callback<TemplesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TemplesResponse> call, @NonNull Response<TemplesResponse> response) {
                        int resCode = response.code();

                        Log.i(TAG, "getTempleById -> onResponseCode: " + resCode);

                        if (resCode >= 200 && resCode < 300) {
                            result.onSuccess(response.body());
                        } else {
                            onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                        }
                    }

                    @Override
                    public void onFailure(Call<TemplesResponse> call, @NonNull Throwable t) {
                        Log.i(TAG, "getTempleById -> onFailure ! ");
                        result.onFail(t);
                    }
                });
            }
        });
    }

    void getCalendarInfo(final OnHTTPResult result) {
        handler.post(() -> {
            api.getCalendarInfo().enqueue(new Callback<CalendarResponse>() {
                @Override
                public void onResponse(@NonNull Call<CalendarResponse> call, @NonNull Response<CalendarResponse> response) {
                    int resCode = response.code();

                    Log.i(TAG, "getCalendarInfo -> onResponse: " + resCode);

                    if (resCode >= 200 && resCode < 300) {
                        result.onSuccess(response.body());
                    } else {
                        onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                    }
                }

                @Override
                public void onFailure(Call<CalendarResponse> call, Throwable t) {
                    Log.i(TAG, "getCalendarInfo -> onFailure: " + t.getMessage());
                    result.onFail(t);
                }
            });
        });
    }

    void getEventInfo(final int id, final OnHTTPResult result) {
        handler.post(() -> api.getEventInfo(id).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
                int resCode = response.code();

                Log.i(TAG, "getEventInfo -> onResponse: " + resCode);

                if (resCode >= 200 && resCode < 300) {
                    result.onSuccess(response.body());
                } else {
                    onFailure(null, new HTTPException(HTTPCode.findByCode(resCode)));
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "getEventInfo -> onFailure: " + t.getMessage());
                result.onFail(t);
            }
        }));
    }
}
