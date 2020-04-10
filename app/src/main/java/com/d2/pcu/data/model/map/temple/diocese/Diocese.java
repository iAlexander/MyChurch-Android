package com.d2.pcu.data.model.map.temple.diocese;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.d2.pcu.data.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Diocese extends BaseModel {

    @SerializedName("id") private int id;

    @SerializedName("name") private String name;

    public Diocese() {
        this.id = -1;
        this.name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Diocese{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
