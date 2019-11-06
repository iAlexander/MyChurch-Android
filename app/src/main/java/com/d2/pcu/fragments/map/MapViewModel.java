package com.d2.pcu.fragments.map;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.utils.Locator;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MapViewModel extends AndroidViewModel {

    private Locator locator;

    private Repository repository;

    private LiveData<LatLng> location;
    private LiveData<List<Temple>> templesLiveData = new MutableLiveData<>();

    public MapViewModel(@NonNull Application application) {
        super(application);

        repository = App.getInstance().getRepositoryInstance();
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

    public LiveData<List<Temple>> getTemplesLiveData() {
        return templesLiveData;
    }
}
