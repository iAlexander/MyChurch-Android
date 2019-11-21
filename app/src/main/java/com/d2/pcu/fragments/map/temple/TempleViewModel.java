package com.d2.pcu.fragments.map.temple;

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
import com.d2.pcu.listeners.OnLoadingEnableListener;

public class TempleViewModel extends ViewModel {

    private OnBackButtonClickListener onBackButtonClickListener;

    private OnLoadingEnableListener onLoadingEnableListener;

    private Repository repository;

    private LiveData<Temple> templeLiveData;

    public TempleViewModel() {
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

    void setOnLoadingEnableListener(OnLoadingEnableListener onLoadingEnableListener) {
        this.onLoadingEnableListener = onLoadingEnableListener;
    }

    void loadTempleInfoById(int id) {
        repository.getTempleById(id);
    }

    public LiveData<Temple> getTempleLiveData() {
        return templeLiveData;
    }

    void enableLoading() {
        if (onLoadingEnableListener != null) {
            onLoadingEnableListener.enableLoading(true);
        }
    }

    void disableLoading() {
        if (onLoadingEnableListener != null) {
            onLoadingEnableListener.enableLoading(false);
        }
    }

    public void onBackButtonPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
