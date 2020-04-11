package com.d2.pcu.utils;

import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Locator {

    private FusedLocationProviderClient client;
    private MutableLiveData<LatLng> currentLocation = new MutableLiveData<>();
    private LatLng defaultKyivLocation = new LatLng(50.4902564, 30.481516);

    public Locator(Context context) {
        client = LocationServices.getFusedLocationProviderClient(context);
    }

    private void getCurrent() {
        Task<Location> result = client.getLastLocation();

        result.addOnCompleteListener(task -> {
            Location location = result.getResult();

            LatLng current; {
                if (location != null) {
                    current = new LatLng(location.getLatitude(), location.getLongitude());
                } else {
                    current = getDefaultKyivLocation();
                }
            }

            currentLocation.setValue(current);
        });
    }

    public LiveData<LatLng> getCurrentLocation() {
        getCurrent();
        return currentLocation;
    }

    public LatLng getDefaultKyivLocation() {
        return defaultKyivLocation;
    }

}
