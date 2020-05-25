package com.d2.pcu.data.responses;

import com.google.gson.annotations.SerializedName;

public class BoolDataResponse<T> extends OnMasterResponse {

    private boolean ok;

    @SerializedName("data")
    private T data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
