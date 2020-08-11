package com.d2.pcu.fragments.cabinet.donate.pay;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.App;
import com.d2.pcu.R;
import com.d2.pcu.data.Repository;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.InfoDialogListener;
import com.d2.pcu.utils.Constants;

public class LiqWebViewModel extends BaseViewModel {

    private final Repository repository;
    private final LiveData<String> paymentData;
    private InfoDialogListener infoDialogListener;

    public LiqWebViewModel() {
        repository = App.getInstance().getRepositoryInstance();
        paymentData = Transformations.switchMap(repository.getTransport().getPaymentChannel(), MutableLiveData::new);
    }

    public void setInfoDialogListener(InfoDialogListener infoDialogListener) {
        this.infoDialogListener = infoDialogListener;
    }

    public void getForm() {
        repository.getCheckOut(Constants.PaymentAction.DONATE, Constants.PAYMENT_COMPLETE);
    }

    public void getForm(float amount) {
        repository.getCheckOutSubscription(Constants.PaymentAction.SUBSCRIBE, Constants.PAYMENT_COMPLETE, amount, repository.getCredentials(Constants.ACCESS_TOKEN));
    }

    public LiveData<String> getPaymentData() {
        return paymentData;
    }

    public void onCompletePayment(View view) {
        if (infoDialogListener != null) {
            infoDialogListener.showInfoDialog(R.string.thanks_for_donate, R.string.ok);
            onBackPressed(view);
        }

    }
}
