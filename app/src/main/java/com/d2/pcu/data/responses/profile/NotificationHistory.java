package com.d2.pcu.data.responses.profile;

import com.d2.pcu.data.model.profile.NotificationHistoryItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NotificationHistory {

    @SerializedName("list")
    private List<NotificationHistoryItem> notificationList;

    public List<NotificationHistoryItem> getNotificationList() {
        if (notificationList == null) notificationList = new ArrayList<>();
        return notificationList;
    }

    public void setNotificationList(List<NotificationHistoryItem> notificationList) {
        this.notificationList = notificationList;
    }
}
