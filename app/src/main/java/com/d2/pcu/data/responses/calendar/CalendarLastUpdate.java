package com.d2.pcu.data.responses.calendar;

import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class CalendarLastUpdate extends OnMasterResponse {

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
