package com.d2.pcu.data.responses.profile;

import com.d2.pcu.data.model.profile.UserProfile;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

public class GetUserProfileResponse extends OnMasterResponse {

    private boolean ok;

    @SerializedName("data")
    private UserProfile userProfile;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
