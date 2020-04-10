package com.d2.pcu.data.model.map.temple;

import com.google.gson.annotations.SerializedName;

public class WorkType {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("groupId")
    private String groupId;

    @SerializedName("text")
    private String text;

    public WorkType() {
        this.id = -1;
        this.title = "";
        this.groupId = "";
        this.text = "";
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "WorkType{" +
                "id=" + id +
                ", type='" + title + '\'' +
                ", groupId='" + groupId + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
