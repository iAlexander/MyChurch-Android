package com.d2.pcu.data.responses.profile;

import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

public class ProfileSignUpResponse extends OnMasterResponse {

    @SerializedName("ok")
    private boolean ok;

    @SerializedName("data")
    private ProfileAccessTokenWrapperResponse profileAccessTokenWrapperResponse;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ProfileAccessTokenWrapperResponse getProfileAccessTokenWrapperResponse() {
        return profileAccessTokenWrapperResponse;
    }

    public void setProfileAccessTokenWrapperResponse(ProfileAccessTokenWrapperResponse profileAccessTokenWrapperResponse) {
        this.profileAccessTokenWrapperResponse = profileAccessTokenWrapperResponse;
    }
}
