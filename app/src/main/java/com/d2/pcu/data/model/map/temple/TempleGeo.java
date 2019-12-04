package com.d2.pcu.data.model.map.temple;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.d2.pcu.data.model.BaseModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class TempleGeo extends BaseModel {

    @SerializedName("id") private int id;

    @SerializedName("street") private String street;

    @SerializedName("index") private String index;

    @SerializedName("lt") private float lt;

    @SerializedName("lg") private float lg;

    @SerializedName("region") private String region;

    @SerializedName("district") private String district;

    @SerializedName("locality") private String locality;

    public TempleGeo() {
        this.id = 0;
        this.street = "";
        this.index = "";
        this.lt = 0f;
        this.lg = 0f;
        this.region = "";
        this.district = "";
        this.locality = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
        notifyPropertyChanged(BR.street);
    }

    @Bindable
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
        notifyPropertyChanged(BR.index);
    }

    public float getLt() {
        return lt;
    }

    public void setLt(float lt) {
        this.lt = lt;
    }

    public float getLg() {
        return lg;
    }

    public void setLg(float lg) {
        this.lg = lg;
    }

    public LatLng getLatLng() {
        return new LatLng(lt, lg);
    }

    @Bindable
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
        notifyPropertyChanged(BR.region);
    }

    @Bindable
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
        notifyPropertyChanged(BR.district);
    }

    @Bindable
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
        notifyPropertyChanged(BR.locality);
    }

    @Override
    public String toString() {
        return "TempleGeo{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", index='" + index + '\'' +
                ", lt=" + lt +
                ", lg=" + lg +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", locality='" + locality + '\'' +
                '}';
    }
}
