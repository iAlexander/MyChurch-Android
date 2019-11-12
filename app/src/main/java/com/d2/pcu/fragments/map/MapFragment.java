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
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.MapFragmentBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.listeners.OnMoreTempleInfoClickListener;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.CustomClusterRenderer;
import com.d2.pcu.utils.MockCreator;
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
        adapter = new TemplesAdapter(
                new OnTempleClickListener() {
                    @Override
                    public void onMoreTempleInfoClick(BaseTemple temple) {
                        String serializedTemple = new Gson().toJson(temple);

                        if (onMoreTempleInfoClickListener != null) {
                            if (temple.getType().equals("Monastir")) {
                                onMoreTempleInfoClickListener.onTempleInfoClick(serializedTemple, Constants.TEMPLE_TYPE_CATHEDRAL);
                            } else {
                                onMoreTempleInfoClickListener.onTempleInfoClick(serializedTemple, Constants.TEMPLE_TYPE_CHURCH);
                            }
                        }
                    }
                },
                new OnItemScrollListener() {
                    @Override
                    public void onItemStop(LatLng latLng) {
                        moveCameraToLocation(latLng);
                    }
                });

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);
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

                    Log.d(TAG, "onScrollStateChanged: " + position);
                }
            }
        });


        if (checkPermission()) {
            viewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
                @Override
                public void onChanged(LatLng latLng) {
                    viewModel.loadData();
                }
            });
        }

        viewModel.getTemplesLiveData().observe(getViewLifecycleOwner(), new Observer<List<BaseTemple>>() {
            @Override
            public void onChanged(List<BaseTemple> temples) {
                adapter.setTemples(temples);
                if (clusterManager != null) {
                    clusterManager.clearItems();
                    clusterManager.addItems(temples);
                }
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

                return false;
            }
        });

        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setOnCameraIdleListener(clusterManager);


//        viewModel.getLocation().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
//            @Override
//            public void onChanged(LatLng latLng) {
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f));
//            }
//        });

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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMoreTempleInfoClickListener = null;
    }
}
