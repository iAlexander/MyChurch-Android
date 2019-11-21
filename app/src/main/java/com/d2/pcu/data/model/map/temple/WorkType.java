package com.d2.pcu.data.model.map.temple;

import com.google.gson.annotations.SerializedName;

public class WorkType {

    @SerializedName("id") private int id;

    @SerializedName("type") private String type;

    public WorkType() {
        this.id = 0;
        this.type = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WorkType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
