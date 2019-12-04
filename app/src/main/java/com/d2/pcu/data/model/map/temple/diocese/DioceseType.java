package com.d2.pcu.data.model.map.temple.diocese;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.d2.pcu.data.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class DioceseType extends BaseModel {

    @SerializedName("id") private int id;

    @SerializedName("type") private String type;

    public DioceseType() {
        this.id = 0;
        this.type = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Override
    public String toString() {
        return "DioceseType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
