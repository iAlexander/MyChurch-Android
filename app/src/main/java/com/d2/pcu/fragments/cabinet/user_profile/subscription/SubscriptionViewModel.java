package com.d2.pcu.fragments.cabinet.user_profile.subscription;

import androidx.lifecycle.LiveData;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.data.model.profile.PaymentHistoryItem;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.fragments.cabinet.donate.OnDonatesClickListener;
import com.d2.pcu.utils.Constants;

import java.util.List;

public class SubscriptionViewModel extends BaseViewModel {

    private Repository repository;
    private LiveData<UserProfile> userProfileLiveData;
    private LiveData<List<PaymentHistoryItem>> paymentHistory;

    private OnDonatesClickListener listener;



    public SubscriptionViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        userProfileLiveData = repository.getTransport().getUserProfileChannel();

        paymentHistory = repository.getTransport().getPaymentsChannel();
    }

    public LiveData<UserProfile> getUserProfileLiveData() {
        return userProfileLiveData;
    }

    public LiveData<List<PaymentHistoryItem>> getPaymentHistory() {
        return paymentHistory;
    }

    public void refreshData(){
        repository.getUserProfile(repository.getCredentials(Constants.ACCESS_TOKEN));
        repository.getPaymentHistory();
    }

    public void subscribe(float value){
        if (listener != null) {
            listener.onDonatesSubscribe(value);
        }
    }

    public void unSubscribe(){
        enableLoading();
        repository.unsubscribe(repository.getCredentials(Constants.ACCESS_TOKEN), ()->{
            repository.getUserProfile(repository.getCredentials(Constants.ACCESS_TOKEN));
            disableLoading();
        });
    }

    void setListener(OnDonatesClickListener listener) {
        this.listener = listener;
    }

    public interface OnRequestResult {
        void onSuccess();
    }
}
