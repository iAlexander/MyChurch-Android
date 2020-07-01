package com.d2.pcu.data.model.map.temple;

import com.d2.pcu.data.model.BaseModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.Objects;

public class BaseTemple extends BaseModel implements ClusterItem {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("distance")
    private double distance;

    @SerializedName("lt")
    private double lt;

    @SerializedName("lg")
    private double lg;

    @SerializedName("type")
    private String type;

    @SerializedName("location")
    private String location;

    @SerializedName("locality")
    private String locality;

    public BaseTemple() {
        id = -1;
        name = "";
        distance = 0;
        lt = 0;
        lg = 0;
        type = "";
        location = "";
        locality = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLt() {
        return lt;
    }

    public void setLt(double lt) {
        this.lt = lt;
    }

    public double getLg() {
        return lg;
    }

    public void setLg(double lg) {
        this.lg = lg;
    }

    public LatLng getLatLng() {
        return new LatLng(lt, lg);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocality() {
        return locality;
    }

    public BaseTemple setLocality(String locality) {
        this.locality = locality;
        return this;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(lt, lg);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return name;
    }

    @Override
    public String toString() {
        return "BaseTemple{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", distance=" + distance +
                ", lt=" + lt +
                ", lg=" + lg +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTemple that = (BaseTemple) o;
        return id == that.id &&
                Double.compare(that.distance, distance) == 0 &&
                Double.compare(that.lt, lt) == 0 &&
                Double.compare(that.lg, lg) == 0 &&
                name.equals(that.name) &&
                type.equals(that.type) &&
                location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, distance, lt, lg, type, location);
    }
}
