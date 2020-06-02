package com.d2.pcu.utils;

import android.content.Context;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

public class Locator {

    private FusedLocationProviderClient client;
    private MutableLiveData<LatLng> currentLocation = new MutableLiveData<>();
    public static final LatLng DEFAULT_KYIV = new LatLng(50.4902564, 30.481516);

    public Locator(Context context, LatLng defaultLocation) {
        client = LocationServices.getFusedLocationProviderClient(context);
        currentLocation.setValue(defaultLocation);
    }

    private void getCurrent() {
        Task<Location> result = client.getLastLocation();

        result.addOnCompleteListener(task -> {
            Location location = result.getResult();

            LatLng current;
            {
                if (location != null) {
                    current = new LatLng(location.getLatitude(), location.getLongitude());
                } else {
                    current = currentLocation.getValue();
                }
            }

            currentLocation.setValue(current);
        });
    }

    public MutableLiveData<LatLng> getCurrentLocationUpdate() {
        getCurrent();
        return currentLocation;
    }

    public MutableLiveData<LatLng> getCurrentLocation() {
        return currentLocation;
    }

}
