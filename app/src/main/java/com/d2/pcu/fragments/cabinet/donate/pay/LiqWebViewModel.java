package com.d2.pcu.fragments.cabinet.donate.pay;

import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.fragments.BaseViewModel;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

public class LiqWebViewModel extends BaseViewModel {

    private final Repository repository;
    private final LiveData<String> paymentData;

    public LiqWebViewModel() {
        repository = App.getInstance().getRepositoryInstance();
        paymentData = Transformations.switchMap(repository.getTransport().getPaymentChannel(), MutableLiveData::new);
    }


    public void getForm() {
        LiqReq req = new LiqReq();
        Gson gson = new Gson();
        String json = gson.toJson(req);
        Timber.e(json);
        String data = new String(Base64.encode(json.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING));
        Timber.e("\"data\" = %s", data);
        String sign = req.getPriKey().concat(data).concat(req.getPriKey());
        byte[] shaSign = new byte[]{};
        try {
            shaSign = StringEncryption.SHA1(sign);
        } catch (NoSuchAlgorithmException e) {
            Timber.e(e);
        }


        String signature = new String(Base64.encode(shaSign, Base64.NO_WRAP));

        Timber.e("\"signature\" = %s", signature);
        Log.e("TAG", "\"signature\" = " + signature);

        repository.postCheckOut(data, signature);

    }

    public LiveData<String> getPaymentData() {
        return paymentData;
    }

    public interface OnRequestResult {
        void onSuccess(String body);
    }

}
