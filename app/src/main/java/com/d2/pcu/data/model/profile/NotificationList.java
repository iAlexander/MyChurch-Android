package com.d2.pcu.data.model.profile;

import com.d2.pcu.data.db.MasterDbModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationList extends MasterDbModel {

    private List<NotificationHistoryItem> items;

    public List<NotificationHistoryItem> getItems() {
        if (items == null) items = new ArrayList<>();
        return items;
    }

    public void setItems(List<NotificationHistoryItem> items) {
        this.items = items;
    }
}
