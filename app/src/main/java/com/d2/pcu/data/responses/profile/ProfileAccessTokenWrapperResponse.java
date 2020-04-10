package com.d2.pcu.data.responses.profile;

import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

public class ProfileAccessTokenWrapperResponse extends OnMasterResponse {

    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
