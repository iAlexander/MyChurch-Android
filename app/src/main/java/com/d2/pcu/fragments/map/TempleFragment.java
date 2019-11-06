package com.d2.pcu.fragments.map;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2.pcu.R;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.databinding.TempleFragmentBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.utils.CustomClusterRenderer;
import com.d2.pcu.utils.MockCreator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

public class TempleFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String TAG = TempleFragment.class.getSimpleName();

    private TempleFragmentBinding binding;
    private TempleViewModel viewModel;

    private TempleAdapter adapter;

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_CODE = 201;

    public static TempleFragment newInstance() {
        return new TempleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.temple_fragment, container, false);

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

        viewModel = ViewModelProviders.of(this).get(TempleViewModel.class);
        adapter = new TempleAdapter();

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);
        binding.templatesListRv.setAdapter(adapter);
        binding.templatesListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        if (checkPermission()) {
            viewModel.loadData();
        }

        new LinearSnapHelper().attachToRecyclerView(binding.templatesListRv);

        // TODO: 05.11.2019 remove mock
        adapter.setTemples(MockCreator.getTemplesMock());
        //
        viewModel.getTemplesLiveData().observe(getViewLifecycleOwner(), new Observer<List<Temple>>() {
            @Override
            public void onChanged(List<Temple> temples) {
                adapter.setTemples(temples);
            }
        });


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

        ClusterManager<Temple> clusterManager = new ClusterManager<Temple>(getContext().getApplicationContext(), googleMap);

        // TODO: 2019-11-06 remove MOCK
        for (Temple temple : MockCreator.getTemplesMock()) {
            clusterManager.addItem(temple);

        }

        final CustomClusterRenderer renderer = new CustomClusterRenderer(getContext(), googleMap, clusterManager);
        clusterManager.setRenderer(renderer);

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Temple>() {
            @Override
            public boolean onClusterClick(Cluster<Temple> cluster) {
                return false;
            }
        });

        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Temple>() {
            @Override
            public boolean onClusterItemClick(Temple temple) {
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
}
