package com.d2.pcu.data.responses.map;

import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseTempleResponse extends OnMasterResponse {

    @SerializedName("data") private List<BaseTemple> baseTemples;

    public List<BaseTemple> getBaseTemples() {
        return baseTemples;
    }

    public void setBaseTemples(List<BaseTemple> baseTemples) {
        this.baseTemples = baseTemples;
    }
}
