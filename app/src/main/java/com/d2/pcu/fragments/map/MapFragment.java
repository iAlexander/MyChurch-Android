package com.d2.pcu.fragments.map;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.databinding.MapFragmentBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnLoadingEnableListener;
import com.d2.pcu.listeners.OnMoreTempleInfoClickListener;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.CustomClusterRenderer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String TAG = MapFragment.class.getSimpleName();

    private MapFragmentBinding binding;
    private MapViewModel viewModel;

    private TemplesAdapter adapter;

    private OnMoreTempleInfoClickListener onMoreTempleInfoClickListener;

    private OnLoadingEnableListener onLoadingEnableListener;

    private ClusterManager<BaseTemple> clusterManager;

    private GoogleMap googleMap;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        viewModel.setOnLoadingEnableListener(onLoadingEnableListener);
        viewModel.enableLoading();

        adapter = new TemplesAdapter(
                new OnTempleClickListener() {
                    @Override
                    public void onMoreTempleInfoClick(BaseTemple temple) {
                        String serializedTemple = new Gson().toJson(temple);

                        if (onMoreTempleInfoClickListener != null) {
                            if (temple.getType().equals(Constants.SIMPLE_CHURCH)) {
                                onMoreTempleInfoClickListener.onTempleInfoClick(serializedTemple, Constants.TEMPLE_TYPE_CHURCH);
                            } else {
                                onMoreTempleInfoClickListener.onTempleInfoClick(serializedTemple, Constants.TEMPLE_TYPE_CATHEDRAL);

                            }
                        }
                    }
                });

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);

        configureSearchView();
        settingTempleRecyclerView();


        if (checkPermission()) {
            viewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
                @Override
                public void onChanged(LatLng latLng) {
                    viewModel.loadData();
                }
            });
        }

        viewModel.getBaseTemplesLiveData().observe(getViewLifecycleOwner(), new Observer<List<BaseTemple>>() {
            @Override
            public void onChanged(List<BaseTemple> temples) {
                adapter.setTemples(temples);
                if (clusterManager != null) {
                    clusterManager.clearItems();
                    clusterManager.addItems(temples);
                }
                viewModel.disableLoading();
            }
        });
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            if (checkPermission()) {
                viewModel.loadData();
            }
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(PERMISSIONS, REQUEST_CODE);
            return false;
        }

        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        this.googleMap = googleMap;

        clusterManager = new ClusterManager<>(getContext().getApplicationContext(), googleMap);

        final CustomClusterRenderer renderer = new CustomClusterRenderer(getContext(), googleMap, clusterManager);
        clusterManager.setRenderer(renderer);

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<BaseTemple>() {
            @Override
            public boolean onClusterClick(Cluster<BaseTemple> cluster) {
                return false;
            }
        });

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<BaseTemple>() {
            @Override
            public boolean onClusterItemClick(BaseTemple temple) {

                adapter.scrollToItem(temple);
                return true;
            }
        });

        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraIdleListener(clusterManager);


        viewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
            @Override
            public void onChanged(LatLng latLng) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f));
            }
        });

    }

    private void settingTempleRecyclerView() {
        binding.templatesListRv.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.templatesListRv.setLayoutManager(linearLayoutManager);
        binding.templatesListRv.setHasFixedSize(true);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.templatesListRv);

        binding.templatesListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(linearLayoutManager);
                    int position = linearLayoutManager.getPosition(centerView);

                    moveCameraToLocation(adapter.onItemScroll(position));
                }
            }
        });
    }

    private void configureSearchView() {
        binding.mapFloatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(viewModel.getLocation().getValue(), 11f));
            }
        });
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

                adapter.scrollToItem(viewModel.getBaseTempleById(templeSuggestion.getId()));
                moveCameraToLocation(baseTemple.getLatLng());
                binding.mapFloatingSearchView.clearSearchFocus();
                binding.mapFloatingSearchView.clearQuery();
            }

            @Override
            public void onSearchAction(String currentQuery) {
                // ignore
            }
        });
    }

    private void moveCameraToLocation(LatLng latLng) {
        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onMoreTempleInfoClickListener = (OnMoreTempleInfoClickListener) context;
        onLoadingEnableListener = (OnLoadingEnableListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMoreTempleInfoClickListener = null;
        onLoadingEnableListener = null;
    }
}
