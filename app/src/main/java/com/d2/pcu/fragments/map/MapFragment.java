package com.d2.pcu.fragments.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
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
import com.google.gson.Gson;

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String TAG = MapFragment.class.getSimpleName();

    private MapFragmentBinding binding;
    private MapViewModel viewModel;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_CODE = 201;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(MapViewModel.class);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);

        if (viewModel.getAdapter() == null) {
            viewModel.setAdapter(new TemplesAdapter());
        }

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

        setUpSearchView();
        setUpTempleRecyclerView();

        if (checkPermission()) {
            viewModel.locationPermissionGranted();
        } else {
            viewModel.locationPermissionDenied();
        }

//        viewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
//            @Override
//            public void onChanged(LatLng latLng) {
//                if (viewModel.getGoogleMap() != null) {
                    //comment this - client want zoom at temple on app start
//                    viewModel.getGoogleMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
//                }
//            }
//        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        viewModel.setGoogleMap(googleMap);

        viewModel.getLocationPermission().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean granted) {
                if (granted) {
                    viewModel.getGoogleMap().setMyLocationEnabled(true);

                    binding.centerMyLocation.setOnClickListener(view -> {
                                viewModel.getGoogleMap()
                                        .animateCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                        viewModel.getLocation().getValue(), 16f)
                                        );
                            }
                    );
                }
            }
        });

        if (viewModel.getGoogleMap() != null) {
            if (viewModel.getAdapter().getItemCount() != 0) {
                viewModel.getGoogleMap().moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                viewModel.getAdapter().onItemScroll(0).getLatLng(), 16f
                        )
                );
            }
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
        binding.mapFloatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (newQuery.length() < 3) {
                    return;
                }

                binding.mapFloatingSearchView.showProgress();
                binding.mapFloatingSearchView.swapSuggestions(viewModel.getBaseTemplesByName(newQuery));
                binding.mapFloatingSearchView.hideProgress();
            }
        });

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
        viewModel.onMarkerStateChange(temple);
        viewModel.getGoogleMap()
                .animateCamera(
                        CameraUpdateFactory.newLatLngZoom(temple.getLatLng(), 16f)
                );
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
