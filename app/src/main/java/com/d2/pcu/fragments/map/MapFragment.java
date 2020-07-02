package com.d2.pcu.fragments.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.databinding.MapFragmentBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.gson.Gson;

import timber.log.Timber;

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    private MapFragmentBinding binding;
    private MapViewModel viewModel;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_CODE = 201;
    private DisplayMetrics metrics;
    private GoogleMap mMap;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        if (viewModel.getAdapter() == null) {
            viewModel.setAdapter(new TemplesAdapter());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);

        viewModel.subscribe();

        viewModel.getAdapter().setOnTempleClickListener(temple -> {
            viewModel.getFullTemple(temple);
            viewModel.enableLoading();

            viewModel.getTempleLiveData().observe(getViewLifecycleOwner(), fullTemple -> {
                String serializedTemple = new Gson().toJson(fullTemple);

                if (onAdditionalFuncMapListener != null) {

                    if (fullTemple.getDioceseType().getName().equals(Constants.CATHEDRAL_CHURCH)) {
                        onAdditionalFuncMapListener.onTempleInfoClick(serializedTemple, Constants.TEMPLE_TYPE_CATHEDRAL);
                    } else {
                        onAdditionalFuncMapListener.onTempleInfoClick(serializedTemple, Constants.TEMPLE_TYPE_CHURCH);
                    }
                }
            });
        });

        viewModel.getAdapter().setOnGetRouteClickListener(direction -> {
            if (onAdditionalFuncMapListener != null) {
                onAdditionalFuncMapListener.getDirectionsOnMap(direction);
            }
        });

        binding.setLifecycleOwner(this);
        binding.setModel(viewModel);

        if (checkPermission()) {
            viewModel.locationPermissionGranted();
        } else {
            viewModel.locationPermissionDenied();
        }

        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        setUpSearchView();
        setUpTempleRecyclerView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap != null) {
            return;
        }
        mMap = googleMap;
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

        viewModel.setGoogleMap(googleMap, metrics);


        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style));

            if (!success) {
                Timber.e("Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Timber.e(e, "Can't find  map style. Error: %s", e.getMessage());
        }


        viewModel.getLocationPermission().observe(this, granted -> {
            if (granted) {
                mMap.setMyLocationEnabled(true);

                binding.centerMyLocation.setOnClickListener(view -> updateDataOnMap());
            }
        });

        if (viewModel.getAdapter().getItemCount() != 0) {
            updateDataOnMap();
        }

    }

    private void updateDataOnMap() {
        if (mMap == null) {
            return;
        }
        LatLngBounds bounds = viewModel.getBounds();
        if (bounds != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    viewModel.getLocationAndCalc().getValue(), 16f)
            );
        }
    }

    private void setUpTempleRecyclerView() {
        binding.templatesListRv.setAdapter(viewModel.getAdapter());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.templatesListRv.setLayoutManager(linearLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.templatesListRv);

        binding.templatesListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(linearLayoutManager);
                    int position = linearLayoutManager.getPosition(centerView);
                    moveCameraToLocation(viewModel.getAdapter().onItemScroll(position));
                }
            }
        });
    }

    private void setUpSearchView() {
        binding.mapFloatingSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (newQuery.length() == 0) {
                binding.mapFloatingSearchView.clearSuggestions();
                return;
            } else if (newQuery.length() < 3) {
                return;
            }
            App.getInstance().logEvent("MAP", newQuery);
            binding.mapFloatingSearchView.showProgress();
            binding.mapFloatingSearchView.swapSuggestions(viewModel.getBaseTemplesByName(newQuery));
            binding.mapFloatingSearchView.hideProgress();
        });
        binding.mapFloatingSearchView.setOnClearSearchActionListener(
                () -> binding.mapFloatingSearchView.clearSuggestions()
        );

        binding.mapFloatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

                TempleSuggestion templeSuggestion = (TempleSuggestion) searchSuggestion;

                BaseTemple baseTemple = viewModel.getBaseTempleById(templeSuggestion.getId());

                if (baseTemple != null) {
                    viewModel.getAdapter().scrollToItem(viewModel.getBaseTempleById(templeSuggestion.getId()));
                    moveCameraToLocation(baseTemple);
                    binding.mapFloatingSearchView.clearSearchFocus();
                    binding.mapFloatingSearchView.clearQuery();
                }
            }

            @Override
            public void onSearchAction(String currentQuery) {
                // ignore
            }
        });
    }

    private void moveCameraToLocation(BaseTemple temple) {
        if (mMap == null) return;
        viewModel.onMarkerStateChange(temple);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temple.getLatLng(), 16f));
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            requestPermissions(PERMISSIONS, REQUEST_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length != 0 && grantResults.length != 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (checkPermission()) {
                    viewModel.locationPermissionGranted();
                } else {
                    viewModel.locationPermissionDenied();
                }
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
        onAdditionalFuncMapListener = (OnAdditionalFuncMapListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLoadingStateChangedListener = null;
        onAdditionalFuncMapListener = null;
    }
}
