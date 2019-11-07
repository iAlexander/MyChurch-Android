package com.d2.pcu.fragments.map.temple.temple_views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentTempleContactsBinding;
import com.d2.pcu.fragments.BaseFragment;

public class TempleContactsFragment extends BaseFragment {

    private FragmentTempleContactsBinding binding;

    public TempleContactsFragment() {
    }

    static TempleContactsFragment newInstance() {
        return new TempleContactsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_temple_contacts, container, false);

        return binding.getRoot();
    }
}
