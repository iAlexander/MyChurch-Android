package com.d2.pcu.data.model.map.temple;

import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

import com.d2.pcu.data.model.BaseModel;
import com.d2.pcu.data.model.map.temple.diocese.Diocese;
import com.d2.pcu.data.model.map.temple.diocese.DioceseType;
import com.d2.pcu.data.model.map.temple.workers.Bishop;
import com.d2.pcu.data.model.map.temple.workers.Priest;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Temple extends BaseModel {

    @SerializedName("id") private int id;

    @SerializedName("name") private String name;

    @SerializedName("phone") private String phone;

    @SerializedName("site") private String site;

    @SerializedName("history") private String history;

    @SerializedName("image") private String imageUrl;

    @SerializedName("bishop") private Bishop bishop;

    @SerializedName("priest") private Priest priest;

    @SerializedName("churchGeo") private TempleGeo templeGeo;

    @SerializedName("diocese") private Diocese diocese;

    @SerializedName("dioceseType") private DioceseType dioceseType;

    public Temple() {
        this.id = 0;
        this.name = "";
        this.phone = "";
        this.site = "";
        this.imageUrl = "";
        this.history = "";
        this.bishop = new Bishop();
        this.priest = new Priest();
        this.templeGeo = new TempleGeo();
        this.diocese = new Diocese();
        this.dioceseType = new DioceseType();
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

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
        notifyPropertyChanged(BR.site);
    }

    @Bindable
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
        notifyPropertyChanged(BR.history);
    }


    public String getImageUrl() {
        return imageUrl;
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Bindable
    public Bishop getBishop() {
        return bishop;
    }

    public void setBishop(Bishop bishop) {
        this.bishop = bishop;
        notifyPropertyChanged(BR.bishop);
    }

    @Bindable
    public Priest getPriest() {
        return priest;
    }

    public void setPriest(Priest priest) {
        this.priest = priest;
        notifyPropertyChanged(BR.priest);
    }

    @Bindable
    public TempleGeo getTempleGeo() {
        return templeGeo;
    }

    public void setTempleGeo(TempleGeo templeGeo) {
        this.templeGeo = templeGeo;
        notifyPropertyChanged(BR.templeGeo);
    }

    @Bindable
    public Diocese getDiocese() {
        return diocese;
    }

    public void setDiocese(Diocese diocese) {
        this.diocese = diocese;
        notifyPropertyChanged(BR.diocese);
    }

    @Bindable
    public DioceseType getDioceseType() {
        return dioceseType;
    }

    public void setDioceseType(DioceseType dioceseType) {
        this.dioceseType = dioceseType;
        notifyPropertyChanged(BR.dioceseType);
    }
}
