package com.d2.pcu.data.responses.temples;

import com.d2.pcu.data.model.map.temple.BaseTemple;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataTemples extends OnMasterResponse {

    @SerializedName("list")
    private List<BaseTemple> list;

    public List<BaseTemple> getList() {
        return list;
    }

    public void setList(List<BaseTemple> list) {
        this.list = list;
    }
}
