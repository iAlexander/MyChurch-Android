package com.d2.pcu.fragments.map.temple.temple_views;

import android.view.View;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;
import com.google.android.gms.maps.model.LatLng;

public class TempleContactsViewModel extends BaseViewModel {

    private OnAdditionalFuncMapListener onAdditionalFuncMapListener;

    private Temple temple;

    public TempleContactsViewModel() {
    }

    public void setTemple(Temple temple) {
        this.temple = temple;
    }

    public Temple getTemple() {
        return temple;
    }

    void setOnAdditionalFuncMapListener(OnAdditionalFuncMapListener onAdditionalFuncMapListener) {
        this.onAdditionalFuncMapListener = onAdditionalFuncMapListener;
    }

    public void onGetDirectionsClick(View view) {
        if (onAdditionalFuncMapListener != null) {
            onAdditionalFuncMapListener.getDirectionsOnMap(new LatLng(temple.getLt(), temple.getLg()));
        }
    }
}
