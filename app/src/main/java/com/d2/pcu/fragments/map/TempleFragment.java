package com.d2.pcu.fragments.map;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2.pcu.R;
import com.d2.pcu.databinding.TempleFragmentBinding;
import com.d2.pcu.fragments.BaseFragment;

import java.lang.reflect.Array;

public class TempleFragment extends BaseFragment {

    private static final String TAG = TempleFragment.class.getSimpleName();

    private TempleFragmentBinding binding;
    private TempleViewModel viewModel;

    private static final String[] PERMISSIONS = new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TempleViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setModel(viewModel);
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(PERMISSIONS, REQUEST_CODE);
            return false;
        }

        return true;
    }

}
