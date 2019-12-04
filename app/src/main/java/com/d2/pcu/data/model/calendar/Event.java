package com.d2.pcu.data.model.calendar;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {

    @SerializedName("id") private int id;

    @SerializedName("dateOldStyle") private Date oldStyle;

    @SerializedName("dateNewStyle") private Date newStyle;

    @SerializedName("name") private String name;

    @SerializedName("descibe") private String description;

    @SerializedName("conceived") private String conceived;

    public Event() {
        this.id = 0;
        this.oldStyle = new Date();
        this.newStyle = new Date();
        this.name = "";
        this.description = "";
        this.conceived = "";
    }

    public Date getOldStyle() {
        return oldStyle;
    }

    public void setOldStyle(Date oldStyle) {
        this.oldStyle = oldStyle;
    }

    public Date getNewStyle() {
        return newStyle;
    }

    public void setNewStyle(Date newStyle) {
        this.newStyle = newStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConceived() {
        return conceived;
    }

    public void setConceived(String conceived) {
        this.conceived = conceived;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", oldStyle=" + oldStyle +
                ", newStyle=" + newStyle +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", conceived='" + conceived + '\'' +
                '}';
    }
}
