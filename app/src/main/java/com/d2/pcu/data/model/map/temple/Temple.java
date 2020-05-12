package com.d2.pcu.data.model.map.temple;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.d2.pcu.data.model.BaseModel;
import com.d2.pcu.data.model.PcuFile;
import com.d2.pcu.data.model.map.temple.diocese.Diocese;
import com.d2.pcu.data.model.map.temple.diocese.DioceseType;
import com.d2.pcu.data.model.map.temple.workers.Bishop;
import com.d2.pcu.data.model.map.temple.workers.Presiding;
import com.d2.pcu.data.model.map.temple.workers.Priest;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Temple extends BaseModel implements ClusterItem {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("galaDay")
    private Date galaDay;

    @SerializedName("galaDayTitle")
    private String galaDayTitle;

    @SerializedName("phone")
    @Nullable
    private String phone;

    @SerializedName("site")
    @Nullable
    private String site;

    @SerializedName("history")
    @Nullable
    private String history;

    @SerializedName("photos")
    @Nullable
    private List<String> imageUrls;

    @SerializedName("bishop")
    private Bishop bishop;

    @SerializedName("presiding")
    private Presiding presiding;

    @SerializedName("priest")
    private Priest priest;

    @SerializedName("diocese")
    private Diocese diocese;

    @SerializedName("dioceseType")
    private DioceseType dioceseType;

    private String street;

    private int index;

    private double lt;

    private double lg;

    private String region;

    private String district;

    private String locality;

    private String schedule;

    private WorkType workType;

    private List<PcuFile> files;

    public Temple() {
        this.id = 0;
        this.name = "";
        this.galaDay = new Date();
        this.galaDayTitle = "";
        this.phone = "";
        this.site = "";
        this.imageUrls = new ArrayList<>();
        this.history = "";
        this.bishop = new Bishop();
        this.presiding = new Presiding();
        this.priest = new Priest();
        this.diocese = new Diocese();
        this.dioceseType = new DioceseType();
        this.street = "";
        this.index = -1;
        this.lt = -1;
        this.lg = -1;
        this.region = "";
        this.district = "";
        this.locality = "";
        this.schedule = "";
        this.workType = new WorkType();
        this.files = new ArrayList<>();
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

    public Date getGalaDay() {
        return galaDay;
    }

    public void setGalaDay(Date galaDay) {
        this.galaDay = galaDay;
    }

    public String getGalaDayTitle() {
        return galaDayTitle;
    }

    public void setGalaDayTitle(String galaDayTitle) {
        this.galaDayTitle = galaDayTitle;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getSite() {
        return site;
    }

    public void setSite(@Nullable String site) {
        this.site = site;
    }

    @Nullable
    public String getHistory() {
        return history;
    }

    public void setHistory(@Nullable String history) {
        this.history = history;
    }

    @Nullable
    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(@Nullable List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Bishop getBishop() {
        return bishop;
    }

    public void setBishop(Bishop bishop) {
        this.bishop = bishop;
    }

    public Presiding getPresiding() {
        return presiding;
    }

    public void setPresiding(Presiding presiding) {
        this.presiding = presiding;
    }

    public Priest getPriest() {
        return priest;
    }

    public void setPriest(Priest priest) {
        this.priest = priest;
    }

    public Diocese getDiocese() {
        return diocese;
    }

    public void setDiocese(Diocese diocese) {
        this.diocese = diocese;
    }

    public DioceseType getDioceseType() {
        return dioceseType;
    }

    public void setDioceseType(DioceseType dioceseType) {
        this.dioceseType = dioceseType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public List<PcuFile> getFiles() {
        return files;
    }

    public void setFiles(List<PcuFile> files) {
        this.files = files;
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

    public String getStreetLocality(){
        StringBuilder stringBuilder = new StringBuilder();
        if(!TextUtils.isEmpty(street)) stringBuilder.append(street);
        if(!TextUtils.isEmpty(street) && !TextUtils.isEmpty(locality)) stringBuilder.append(", ");
        if(!TextUtils.isEmpty(locality)) stringBuilder.append(locality);
        return stringBuilder.toString();
    }

}
