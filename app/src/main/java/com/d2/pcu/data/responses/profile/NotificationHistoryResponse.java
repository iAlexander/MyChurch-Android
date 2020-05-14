package com.d2.pcu.data.responses.profile;

import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NotificationHistoryResponse extends OnMasterResponse {

    private boolean ok;

    @SerializedName("list")
    private List<NotificationHistoryItem> notificationList;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<NotificationHistoryItem> getNotificationList() {
        if (notificationList == null) notificationList = new ArrayList<>();
        return notificationList;
    }

    public void setNotificationList(List<NotificationHistoryItem> notificationList) {
        this.notificationList = notificationList;
    }
}
