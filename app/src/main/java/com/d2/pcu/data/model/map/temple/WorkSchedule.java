package com.d2.pcu.data.model.map.temple;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class WorkSchedule {

    @SerializedName("id") private int id;

    @SerializedName("day")
    @Nullable
    private String day;

    @SerializedName("time")
    @Nullable
    private String time;

    @SerializedName("schedule") private String schedule;

    @SerializedName("workType") private WorkType workType;

    public WorkSchedule() {
        this.id = 0;
        this.day = "";
        this.time = "";
        this.schedule = "";
        this.workType = new WorkType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getDay() {
        return day;
    }

    public void setDay(@Nullable String day) {
        this.day = day;
    }

    @Nullable
    public String getTime() {
        return time;
    }

    public void setTime(@Nullable String time) {
        this.time = time;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    @Override
    public String toString() {
        return "WorkSchedule{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", schedule='" + schedule + '\'' +
                ", workType=" + workType +
                '}';
    }
}
