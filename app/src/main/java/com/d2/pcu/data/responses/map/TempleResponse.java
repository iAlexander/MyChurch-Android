package com.d2.pcu.data.responses.map;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

public class TempleResponse extends OnMasterResponse {

    @SerializedName("data")
    private Temple temple;

    public Temple getTemple() {
        return temple;
    }

    public void setTemple(Temple temple) {
        this.temple = temple;
    }

    @Override
    public String toString() {
        return "TempleResponse{" +
                "temple=" + temple +
                '}';
    }
}
