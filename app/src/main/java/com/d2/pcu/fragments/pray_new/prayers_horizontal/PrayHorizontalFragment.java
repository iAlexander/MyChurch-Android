package com.d2.pcu.fragments.pray_new.prayers_horizontal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.R;
import com.d2.pcu.databinding.FragmentPraysHorizontalBinding;
import com.d2.pcu.fragments.BaseFragment;
import com.d2.pcu.fragments.pray_new.PrayViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.utils.Constants;

public class PrayHorizontalFragment extends BaseFragment {

    private FragmentPraysHorizontalBinding binding;
    private PrayViewModel viewModel;
    private PrayHorizontalAdapter adapter;

    private OnBackButtonClickListener onBackButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prays_horizontal, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new PrayHorizontalAdapter();

        viewModel = ViewModelProviders.of(getActivity()).get(PrayViewModel.class);
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);

        binding.setModel(viewModel);
        binding.prayersHorizontalVp.setAdapter(adapter);

        if (viewModel.getSelectedType().equals(Constants.PRAY_MORNING)) {
            adapter.setPrays(viewModel.getMorningPrays().getValue());
        } else {
            adapter.setPrays(viewModel.getEveningPrays().getValue());
        }

        binding.prayersHorizontalVp.setCurrentItem(viewModel.getSelectedItem(), false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackButtonClickListener = null;
    }
}
