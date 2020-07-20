package com.d2.pcu.data.model.calendar;

import android.net.Uri;
import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.d2.pcu.BuildConfig;
import com.d2.pcu.data.model.BaseModel;
import com.d2.pcu.utils.converters.DateDataConverter;
import com.d2.pcu.utils.converters.EventGroupDataConverter;
import com.d2.pcu.utils.converters.IconImageDataConverter;

import java.util.Date;
import java.util.Objects;

@Entity
public class CalendarItem extends BaseModel {

    @PrimaryKey
    private int id;

    @TypeConverters(EventGroupDataConverter.class)
    private Group group;

    @TypeConverters(IconImageDataConverter.class)
    private IconImage iconImage;
    @TypeConverters(DateDataConverter.class)
    private Date dateOldStyle;
    @TypeConverters(DateDataConverter.class)
    private Date dateNewStyle;

    private String name;

    private String describe;

    private String conceived;

    private String color;

    private int fasting;

    private int priority;

    public CalendarItem() {
        id = -1;
        group = new Group();
        iconImage = new IconImage();
        dateNewStyle = new Date();
        dateOldStyle = new Date();
        name = "";
        describe = "";
        conceived = "";
        color = "";
        fasting = 0;
        priority = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public IconImage getIconImage() {
        return iconImage;
    }

    public void setIconImage(IconImage iconImage) {
        this.iconImage = iconImage;
    }

    public Date getDateOldStyle() {
        return dateOldStyle;
    }

    public void setDateOldStyle(Date dateOldStyle) {
        this.dateOldStyle = dateOldStyle;
    }

    public Date getDateNewStyle() {
        return dateNewStyle;
    }

    public void setDateNewStyle(Date dateNewStyle) {
        this.dateNewStyle = dateNewStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getConceived() {
        return conceived;
    }

    public void setConceived(String conceived) {
        this.conceived = conceived;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFasting() {
        return fasting;
    }

    public void setFasting(int fasting) {
        this.fasting = fasting;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getImageUrl() {
        if (iconImage == null) return null;
        if (TextUtils.isEmpty(iconImage.getPath()) || TextUtils.isEmpty(iconImage.getName()))
            return null;
        return new Uri.Builder().scheme("http")
                .authority(Uri.parse(BuildConfig.API_BASE_URL).getAuthority())
                .path(iconImage.getPath()).appendPath(iconImage.getName())
                .build().toString();
    }

    @Override
    public String toString() {
        return "CalendarItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarItem that = (CalendarItem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
