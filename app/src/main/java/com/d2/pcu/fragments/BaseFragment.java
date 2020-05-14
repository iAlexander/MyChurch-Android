package com.d2.pcu.fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.listeners.OnNotificationClickListener;

public class BaseFragment extends Fragment {
    private OnBackButtonClickListener onBackButtonClickListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private OnNotificationClickListener onNotificationClickListener;


    /**
     * onBackButtonClickListener
     * onLoadingStateChangedListener
     * onNotificationClickListener
     *
     * @param viewModel BaseViewModel
     */
    public void setViewModelListeners(BaseViewModel viewModel) {
        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
        viewModel.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
        viewModel.setOnNotificationClickListener(onNotificationClickListener);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onBackButtonClickListener = (OnBackButtonClickListener) context;
        onLoadingStateChangedListener = (OnLoadingStateChangedListener) context;
        if (context instanceof OnNotificationClickListener) {
            onNotificationClickListener = (OnNotificationClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        onLoadingStateChangedListener = null;
        onNotificationClickListener = null;
        onBackButtonClickListener = null;
        super.onDetach();
    }
}
