package com.d2.pcu.data.responses.temples;

import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShortTemplesInfoResponse extends OnMasterResponse {

    @SerializedName("data")
    @Expose
    private DataTemples data;

    public DataTemples getData() {
        return data;
    }

    public void setData(DataTemples data) {
        this.data = data;
    }
}
