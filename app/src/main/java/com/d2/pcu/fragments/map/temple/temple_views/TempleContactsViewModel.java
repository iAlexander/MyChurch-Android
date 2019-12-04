package com.d2.pcu.fragments.map.temple.temple_views;

import android.util.Log;
import android.view.View;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

public class TempleContactsViewModel extends ViewModel {


    private static final String TAG = TempleContactsViewModel.class.getSimpleName();

    private OnBackButtonClickListener onBackButtonClickListener;

    private OnLoadingStateChangedListener onLoadingStateChangedListener;

    private Repository repository;

    private LiveData<Temple> templeLiveData;

    public TempleContactsViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        templeLiveData = Transformations.switchMap(repository.getTransport().getTempleChannel(), new Function<Temple, LiveData<Temple>>() {
            @Override
            public LiveData<Temple> apply(Temple input) {
                return new MutableLiveData<>(input);
            }
        });
    }

    void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        this.onLoadingStateChangedListener = onLoadingStateChangedListener;
    }

    void loadTempleInfoById(int id) {
        repository.getTempleById(id);
    }

    public LiveData<Temple> getTempleLiveData() {
        return templeLiveData;
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
        Log.d(TAG, "onGetDirectionsClick: ");
    }

    // TODO: 2019-11-15 MAKE ONE ABSTRACT VIEW HOLDER
}
