package com.d2.pcu.data.model.calendar;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

    @SerializedName("id")
    private int id;

    @SerializedName("dateOldStyle")
    private Date oldStyle;

    @SerializedName("dateNewStyle")
    private Date newStyle;

    @SerializedName("name")
    private String name;

    @SerializedName("describe")
    private String description;

    @SerializedName("conceived")
    private String conceived;

    @SerializedName("fasting")
    private int fasting;

    @SerializedName("icons")
    private List<String> imageUrls;

    public Event() {
        this.id = -1;
        this.oldStyle = new Date();
        this.newStyle = new Date();
        this.name = "";
        this.description = "";
        this.conceived = "";
        this.fasting = -1;
        this.imageUrls = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFasting() {
        return fasting;
    }

    public void setFasting(int fasting) {
        this.fasting = fasting;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
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
                ", fasting=" + fasting +
                ", imageUrls=" + imageUrls +
                '}';
    }
}
