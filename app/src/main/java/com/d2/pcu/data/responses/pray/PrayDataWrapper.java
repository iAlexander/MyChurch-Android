package com.d2.pcu.data.responses.pray;

import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrayDataWrapper extends OnMasterResponse {

    @SerializedName("list")
    private List<Pray> prays;

    public List<Pray> getPrays() {
        return prays;
    }

    public void setPrays(List<Pray> prays) {
        this.prays = prays;
    }

    @Override
    public String toString() {
        return "PrayResponse{" +
                "prays=" + prays +
                '}';
    }
}
