package com.d2.pcu.data.responses.profile;

import com.d2.pcu.data.responses.BoolDataResponse;
import com.google.gson.annotations.SerializedName;

public class PaymentUrlResponse extends BoolDataResponse<PaymentUrlResponse> {

    @SerializedName("url")
    private String url;

    @SerializedName("orderId")
    private String orderId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PaymentUrlResponse{" +
                "url='" + url + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
