package com.d2.pcu.data.responses.diocese;

import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;


public class DioceseResponse extends OnMasterResponse {

    @SerializedName("data")
    private DioceseDataWrapper dioceseDataWrapper;

    public DioceseDataWrapper getDioceseDataWrapper() {
        return dioceseDataWrapper;
    }

    public void setDioceseDataWrapper(DioceseDataWrapper dioceseDataWrapper) {
        this.dioceseDataWrapper = dioceseDataWrapper;
    }
}
