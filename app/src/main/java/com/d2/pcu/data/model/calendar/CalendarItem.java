package com.d2.pcu.data.model.calendar;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.d2.pcu.data.model.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CalendarItem extends BaseModel {

    @SerializedName("id") private int id;

    @SerializedName("date") private Date holidayDate;

    @SerializedName("name") private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
        notifyPropertyChanged(BR.holidayDate);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Override
    public String toString() {
        return "CalendarItem{" +
                "id=" + id +
                ", holidayDate=" + holidayDate +
                ", name='" + name + '\'' +
                '}';
    }
}
