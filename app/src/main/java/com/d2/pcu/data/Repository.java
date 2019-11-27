package com.d2.pcu.data;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;

import com.d2.pcu.data.model.calendar.Event;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.d2.pcu.data.responses.calendar.CalendarResponse;
import com.d2.pcu.data.responses.calendar.EventResponse;
import com.d2.pcu.data.responses.map.BaseTempleResponse;
import com.d2.pcu.data.responses.map.TempleResponse;
import com.d2.pcu.data.responses.map.TemplesResponse;
import com.d2.pcu.ui.error.HTTPException;
import com.d2.pcu.ui.error.OnError;
import com.d2.pcu.ui.error.OnHTTPResult;
import com.d2.pcu.utils.Constants;
import com.google.android.gms.maps.model.LatLng;

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

    public void getBaseTemplesInfo(final LatLng location) {
        netLoader.getBaseTemplesInfo(location.latitude, location.longitude, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                Log.i(TAG, "getBaseTemplesInfo -> onSuccess ");
                channels.getBaseTemplesChannel().postValue(((BaseTempleResponse) response).getBaseTemples());
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    Log.i(TAG, "getBaseTemplesInfo -> onFail !!!");
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getTempleById(int id) {
        netLoader.getTempleById(id, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                Log.i(TAG, "getTempleById -> onSuccess ");
                channels.getTempleChannel().postValue(((TempleResponse) response).getTemple());
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    Log.i(TAG, "getTempleById -> onFail !!!");
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getCalendar() {
        netLoader.getCalendarInfo(new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                channels.getCalendarChannel().postValue(((CalendarResponse)response).getCalendarItems());
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    Log.i(TAG, "getCalendar -> onFail !!!");
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }

    public void getEventInfo(final int id) {
        netLoader.getEventInfo(id, new OnHTTPResult() {
            @Override
            public void onSuccess(OnMasterResponse response) {
                channels.getEventChannel().postValue(((EventResponse) response).getEvent());
            }

            @Override
            public void onFail(Throwable ex) {
                if (onError != null) {
                    Log.i(TAG, "getEventInfo -> onFail !!!");
                    if (ex instanceof HTTPException) {
                        onError.onError(Constants.ERROR_TYPE_SERVER_ERROR);
                    } else {
                        onError.onError(Constants.ERROR_TYPE_NO_CONNECTION);
                    }
                }
            }
        });
    }
}
