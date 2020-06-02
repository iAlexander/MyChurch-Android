package com.d2.pcu.fragments.map;

import android.app.Application;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.utils.CustomClusterRenderer;
import com.d2.pcu.utils.Locator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends AndroidViewModel {
    private Repository repository;

    private MutableLiveData<LatLng> location;
    private LiveData<List<BaseTemple>> baseTemplesLiveData;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private MutableLiveData<Boolean> locationPermission = new MutableLiveData<>(false);

    private GoogleMap googleMap;
    private ClusterManager<BaseTemple> clusterManager;
    private CustomClusterRenderer clusterRenderer;
    private BaseTemple lastClusterMarker;

    private TemplesAdapter adapter;

    private LiveData<Temple> templeLiveData;
    private final Locator locator;

    public MapViewModel(@NonNull Application application) {
        super(application);

        repository = App.getInstance().getRepositoryInstance();
        locator = new Locator(getApplication().getApplicationContext(), repository.getLastLocation());
        location = locator.getCurrentLocation();

        templeLiveData = repository.getTransport().getTempleChannel();

        baseTemplesLiveData = repository.getTransport().getBaseTemplesChannel();
//                Transformations.switchMap(
//                repository.getTransport().getBaseTemplesChannel(),
//                input -> {
//                    return new MutableLiveData<>(input);
//                });

        baseTemplesLiveData.observe(repository, baseTemples -> {
            adapter.setTemples(baseTemples);

            if (googleMap != null) {
                googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                baseTemples.get(0).getLatLng(), 16f
                        )
                );
            }

            if (clusterManager != null) {
                clusterManager.clearItems();
                clusterManager.addItems(baseTemples);
                clusterManager.cluster();
            }
            disableLoading();
        });
        location.observe(repository, latLng -> {
            repository.setLastLocation(latLng);
            loadData();
        });
    }

    /**
     * Loading screen enabler
     *
     * @param onLoadingStateChangedListener - enable or disable loding overlay view
     */
    public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
    }

    void getFullTemple(BaseTemple baseTemple) {
        repository.getTempleById(baseTemple.getId());
    }

    public LiveData<Temple> getTempleLiveData() {
        return templeLiveData;
    }

    /**
     * Getters
     */
    public MutableLiveData<Boolean> getLocationPermission() {
        return locationPermission;
    }

    void setGoogleMap(GoogleMap googleMap, DisplayMetrics metrics) {

        this.googleMap = googleMap;

        clusterManager = new ClusterManager<>(
                getApplication().getApplicationContext(),
                googleMap
        );

        clusterRenderer = new CustomClusterRenderer(
                getApplication().getApplicationContext(), googleMap, clusterManager
        );

        clusterManager.setAlgorithm(new NonHierarchicalViewBasedAlgorithm<BaseTemple>(
                metrics.widthPixels, metrics.heightPixels));
        clusterManager.setRenderer(clusterRenderer);

        clusterManager.setOnClusterClickListener(cluster -> {
            LatLngBounds.Builder builder = LatLngBounds.builder();
            for (ClusterItem item : cluster.getItems()) {
                builder.include(item.getPosition());
            }
            // Get the LatLngBounds
            final LatLngBounds bounds = builder.build();

            // Animate camera to the bounds
            try {
               googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        });

        clusterManager.setOnClusterItemClickListener(temple -> {
            onMarkerStateChange(temple);
            getAdapter().scrollToItem(temple);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temple.getLatLng(), 16f));
            return true;
        });

        if (baseTemplesLiveData.getValue() != null) {
            clusterManager.clearItems();
            clusterManager.addItems(baseTemplesLiveData.getValue());
            clusterManager.cluster();
        }

        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraIdleListener(clusterManager);
    }

    TemplesAdapter getAdapter() {
        return adapter;
    }

    void setAdapter(TemplesAdapter adapter) {
        this.adapter = adapter;
    }

    LatLngBounds getBounds() {
        LatLng latLng = location.getValue();
        adapter.updateDistance(location.getValue());
        return adapter.getNearest(latLng);
    }

    LiveData<LatLng> getLocationAndCalc() {
        adapter.updateDistance(location.getValue());
        return location;
    }


    /**
     * Enable loading overlayView at main activity
     */
    void enableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(true);
        }
    }

    /**
     * Disable loading overlayView at main activity
     */
    void disableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
    }

    /**
     * Load data from server into channel
     */
    private void loadData() {
        enableLoading();
        repository.getShortTemplesInfo(location.getValue());
    }

    /**
     * Search temples by name into temples list
     *
     * @param query - start symbols
     * @return List of TempleSuggestions for search view
     */
    List<TempleSuggestion> getBaseTemplesByName(String query) {

        List<TempleSuggestion> templeSuggestions = new ArrayList<>();

        if (baseTemplesLiveData != null) {

            for (BaseTemple temple : baseTemplesLiveData.getValue()) {
                if (temple.getName().contains(query) || temple.getName().toLowerCase().contains(query)) {
                    templeSuggestions.add(new TempleSuggestion(temple.getName(), temple.getLocation(), temple.getId()));
                }
            }

        }

        return templeSuggestions;
    }

    /**
     * Search temple by
     *
     * @param id of temple which searched
     * @return searched temple or @null
     */
    BaseTemple getBaseTempleById(int id) {
        for (BaseTemple temple : baseTemplesLiveData.getValue()) {
            if (temple.getId() == id) {
                return temple;
            }
        }

        return null;
    }

    /**
     * Method which if permission granted get location service and change location liveData
     */
    void locationPermissionGranted() {
        location = locator.getCurrentLocationUpdate();
        locationPermission.setValue(true);
    }

    void locationPermissionDenied() {
        location.setValue(location.getValue());
        locationPermission.setValue(false);
    }

    /**
     * Search marker in cluster renderer by
     *
     * @param temple element on map fragment which icon need to change
     *               and change icon for current selected and unselected previous
     */
    synchronized void onMarkerStateChange(BaseTemple temple) {
        if (lastClusterMarker != null) {
            Marker prevMarker = clusterRenderer.getMarker(lastClusterMarker);
            if (prevMarker != null) {
                prevMarker.setIcon(
                        clusterRenderer.bitmapDescriptorFromVector(R.drawable.map_pin)
                );
            }
        }

        Marker currentMarker = clusterRenderer.getMarker(temple);
        if (currentMarker == null) {
            return;
        }

        currentMarker.setIcon(
                clusterRenderer.bitmapDescriptorFromVector(R.drawable.map_pin_active)
        );

        lastClusterMarker = temple;
    }

}
