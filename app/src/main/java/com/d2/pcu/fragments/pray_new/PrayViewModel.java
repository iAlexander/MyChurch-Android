package com.d2.pcu.fragments.pray_new;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.utils.Constants;

import java.util.List;

public class PrayViewModel extends BaseViewModel {

    private Repository repository;

    private LiveData<List<Pray>> morningPrays;
    private LiveData<List<Pray>> eveningPrays;

    private OnBackButtonClickListener onBackButtonClickListener;

    int selectedItem;
    String selectedType;

    public PrayViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        morningPrays = repository.getTransport().getMorningServerPraysChannel();
        eveningPrays = repository.getTransport().getEveningServerPraysChannel();

        loadPrays();
    }

    void loadPrays() {
        repository.getPrays();
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
    }

    @Override
    public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
        super.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
    }

    public LiveData<List<Pray>> getMorningPrays() {
        return morningPrays;
    }

    public LiveData<List<Pray>> getEveningPrays() {
        return eveningPrays;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
