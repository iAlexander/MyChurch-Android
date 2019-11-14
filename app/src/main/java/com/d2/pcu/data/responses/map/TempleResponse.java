package com.d2.pcu.data.responses.map;

import com.d2.pcu.data.model.map.temple.Temple;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TempleResponse extends OnMasterResponse {

    @SerializedName("data") private List<Temple> temples;

    public List<Temple> getTemples() {
        return temples;
    }

    public void setTemples(List<Temple> temples) {
        this.temples = temples;
    }
}
