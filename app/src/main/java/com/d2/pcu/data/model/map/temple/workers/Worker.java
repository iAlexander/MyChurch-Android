package com.d2.pcu.data.model.map.temple.workers;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.d2.pcu.data.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Worker extends BaseModel {

    @SerializedName("id") protected int id;

    @SerializedName("name") protected String name;

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
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
