package com.d2.pcu.fragments.pray_new;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.utils.Constants;

import java.util.List;

public class PrayViewModel extends BaseViewModel {

    private Repository repository;

    private LiveData<List<Pray>> morningPrays;
    private LiveData<List<Pray>> eveningPrays;
    private MutableLiveData<String> trackTitle;

    int selectedItem;
    String selectedType;

    public PrayViewModel() {
        repository = App.getInstance().getRepositoryInstance();
        morningPrays = repository.getPraysLiveData(Constants.PRAY_MORNING);
        eveningPrays = repository.getPraysLiveData(Constants.PRAY_EVENING);

//        morningPrays = repository.getTransport().getMorningServerPraysChannel();
//        eveningPrays = repository.getTransport().getEveningServerPraysChannel();
        trackTitle = repository.getTransport().getTrackTitle();

        loadPrays();
    }

    void loadPrays() {
        repository.checkPrayUpdate();
    }

    void forceLoadPrays() {
        repository.getPrays(Constants.PRAY_FORCE_UPDATE);
    }

//    @Override
//    public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener onLoadingStateChangedListener) {
//        super.setOnLoadingStateChangedListener(onLoadingStateChangedListener);
//    }


    public LiveData<List<Pray>> getMorningPrays() {
        return morningPrays;
    }

    public LiveData<List<Pray>> getEveningPrays() {
        return eveningPrays;
    }

    public MutableLiveData<String> getTrackTitle() {
        return trackTitle;
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public String getSelectedType() {
        return selectedType;
    }

}
