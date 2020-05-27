package com.d2.pcu.data.model.profile;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.d2.pcu.data.db.MasterDbModel;
import com.d2.pcu.utils.converters.DateDataConverter;

import java.util.Date;
import java.util.Objects;

@Entity
public class NotificationHistoryItem extends MasterDbModel {
    @PrimaryKey
    private int id = 0;
    private String title = "";
    private String text = "";
    private boolean read = false;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationHistoryItem that = (NotificationHistoryItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NotificationHistoryItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }
}
