package com.d2.pcu.data.model;

import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LastUpdate extends OnMasterResponse {

    @SerializedName("hash")
    private String hash;
    @SerializedName("lastUpdate")
    private Date lastUpdate;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "CalendarLastUpdate{" +
                "hash='" + hash + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
