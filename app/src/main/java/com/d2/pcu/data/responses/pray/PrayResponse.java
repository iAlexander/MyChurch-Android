package com.d2.pcu.data.responses.pray;

import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrayResponse extends OnMasterResponse {

    @SerializedName("data")
    private PrayDataWrapper prayDataWrapper;

    public PrayDataWrapper getPrayDataWrapper() {
        return prayDataWrapper;
    }

    public void setPrayDataWrapper(PrayDataWrapper prayDataWrapper) {
        this.prayDataWrapper = prayDataWrapper;
    }
}
