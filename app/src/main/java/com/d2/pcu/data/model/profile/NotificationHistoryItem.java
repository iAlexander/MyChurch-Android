package com.d2.pcu.data.model.profile;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.d2.pcu.utils.converters.DateDataConverter;

import java.util.Date;

@Entity
public class NotificationHistoryItem {
    //{
    //        "id": 3,
    //        "title": "Поздравляем с храмовым праздником",
    //        "read": false,
    //        "createdAt": "2020-05-12T16:54:46.3391242+03:00"
    //      }
    @PrimaryKey
    private int id=0;
    private String title="";
    private boolean read=false;
    @TypeConverters(DateDataConverter.class)
    private Date createdAt = new Date();

    public NotificationHistoryItem() {
    }

    @Ignore
    public NotificationHistoryItem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
