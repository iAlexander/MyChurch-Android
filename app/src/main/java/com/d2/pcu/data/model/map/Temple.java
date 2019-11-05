package com.d2.pcu.data.model.map;

import androidx.databinding.Bindable;

import com.d2.pcu.data.model.BaseModel;

public class Temple extends BaseModel {

    private String title;

    private String distance;

    private String description;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Temple{" +
                "title='" + title + '\'' +
                ", distance='" + distance + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
