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
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.utils.Locator;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MapViewModel extends AndroidViewModel {

    private static final String TAG = MapViewModel.class.getSimpleName();

    private Locator locator;

    private Repository repository;

    private LiveData<LatLng> location;
    private LiveData<List<BaseTemple>> baseTemplesLiveData;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    public MapViewModel(@NonNull Application application) {
        super(application);

        repository = App.getInstance().getRepositoryInstance();
        locator = new Locator(getApplication().getApplicationContext());

        location = Transformations.switchMap(locator.getCurrentLocation(), new Function<LatLng, LiveData<LatLng>>() {
            @Override
            public LiveData<LatLng> apply(LatLng input) {
                return new MutableLiveData<>(input);
            }
        });

        baseTemplesLiveData = Transformations.switchMap(repository.getTransport().getBaseTemplesChannel(), new Function<List<BaseTemple>, LiveData<List<BaseTemple>>>() {
            @Override
            public LiveData<List<BaseTemple>> apply(List<BaseTemple> input) {

                Collections.sort(input, new Comparator<BaseTemple>() {
                    @Override
                    public int compare(BaseTemple o1, BaseTemple o2) {
                        return Double.compare(o1.getDistance(), o2.getDistance());
                    }
                });

                return new MutableLiveData<>(input);
            }
        });
    }

    public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
    }

    void enableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(true);
        }
    }

    void disableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
    }

    void loadData() {
        repository.getBaseTemplesInfo(location.getValue());
    }

    LiveData<LatLng> getLocation() {
        return location;
    }

    LiveData<List<BaseTemple>> getBaseTemplesLiveData() {
        return baseTemplesLiveData;
    }


    List<TempleSuggestion> getBaseTemplesByName(String query) {

        List<TempleSuggestion> templeSuggestions = new ArrayList<>();

        if (baseTemplesLiveData != null) {

            for (BaseTemple temple : baseTemplesLiveData.getValue()) {
                if (temple.getName().contains(query) || temple.getName().toLowerCase().contains(query)) {
                    templeSuggestions.add(new TempleSuggestion(temple.getName(), temple.getId()));
                }
            }

        }

        return templeSuggestions;
    }

    BaseTemple getBaseTempleById(int id) {
        for (BaseTemple temple : baseTemplesLiveData.getValue()) {
            if (temple.getId() == id) {
                return temple;
            }
        }

        return null;
    }

}
