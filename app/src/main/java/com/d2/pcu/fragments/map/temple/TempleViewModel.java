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
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;

import java.util.Calendar;

public class TempleViewModel extends BaseViewModel {

    private OnBackButtonClickListener onBackButtonClickListener;

    private Repository repository;

    private Temple temple;

    public TempleViewModel() {
        repository = App.getInstance().getRepositoryInstance();
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


    public void onBackButtonPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }


}
