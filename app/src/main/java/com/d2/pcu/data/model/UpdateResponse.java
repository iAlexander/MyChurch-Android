package com.d2.pcu.data.model;

import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

public class UpdateResponse extends OnMasterResponse {

    @SerializedName("data")
    private LastUpdate lastUpdate;

    public LastUpdate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LastUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
