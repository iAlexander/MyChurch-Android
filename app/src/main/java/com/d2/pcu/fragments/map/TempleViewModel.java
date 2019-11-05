package com.d2.pcu.fragments.map;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.data.model.map.Temple;
import com.d2.pcu.utils.Locator;
import com.google.android.gms.maps.model.LatLng;

public class TempleViewModel extends AndroidViewModel {

    private Temple model = new Temple();
    private Locator locator;

    private LiveData<LatLng> location;

    public TempleViewModel(@NonNull Application application) {
        super(application);

        locator = new Locator(getApplication().getApplicationContext());
    }

    void loadData() {
        location = Transformations.switchMap(locator.getCurrentLocation(), new Function<LatLng, LiveData<LatLng>>() {
            @Override
            public LiveData<LatLng> apply(LatLng input) {
                return new MutableLiveData<>(input);
            }
        });
    }

    LiveData<LatLng> getLocation() {
        return location;
    }
}
