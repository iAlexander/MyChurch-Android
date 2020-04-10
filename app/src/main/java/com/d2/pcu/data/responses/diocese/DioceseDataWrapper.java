package com.d2.pcu.data.responses.diocese;

import com.d2.pcu.data.model.diocese.Diocese;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DioceseDataWrapper extends OnMasterResponse {

    @SerializedName("list")
    private List<Diocese> dioceseList;

    public List<Diocese> getDioceseList() {
        return dioceseList;
    }

    public void setDioceseList(List<Diocese> dioceseList) {
        this.dioceseList = dioceseList;
    }
}
