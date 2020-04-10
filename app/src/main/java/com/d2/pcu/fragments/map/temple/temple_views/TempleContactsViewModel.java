package com.d2.pcu.fragments.map.temple.temple_views;

import android.view.View;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class TempleContactsViewModel extends ViewModel {

    private static final String TAG = TempleContactsViewModel.class.getSimpleName();

    private OnBackButtonClickListener onBackButtonClickListener;
    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;
    private OnLoadingStateChangedListener onLoadingStateChangedListener;
    private Repository repository;

    private MutableLiveData<Boolean> isOpen = new MutableLiveData<>();

    private Temple temple;

    public TempleContactsViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        checkTime();
    }

    public void setTemple(Temple temple) {
        this.temple = temple;
    }

    public Temple getTemple() {
        return temple;
    }

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setOnAdditionalFuncMapListener(OnAdditionalFuncMapListener onAdditionalFuncMapListener) {
        this.onAdditionalFuncMapListener = onAdditionalFuncMapListener;
    }

    void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
    }

    void enableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(true);
        }
    }

    void disableLoading() {
        if (onLoadingStateChangedListener != null) {
            onLoadingStateChangedListener.enableLoading(false);
        }
    }

    public void onBackButtonPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }

    public void onGetDirectionsClick(View view) {
        if (onAdditionalFuncMapListener != null) {
            onAdditionalFuncMapListener.getDirectionsOnMap(new LatLng(temple.getLt(), temple.getLg()));
        }
    }

    private void checkTime() {
        Calendar calendar = Calendar.getInstance();

        isOpen.setValue(calendar.get(Calendar.HOUR_OF_DAY) < 19 && calendar.get(Calendar.HOUR_OF_DAY) >= 9);
    }

    public MutableLiveData<Boolean> getIsOpen() {
        return isOpen;
    }
}
