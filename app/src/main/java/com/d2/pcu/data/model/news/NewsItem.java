package com.d2.pcu.data.model.news;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.d2.pcu.data.model.BaseModel;
import com.d2.pcu.utils.converters.DateDataConverter;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;

@Entity
public class NewsItem extends BaseModel {

    @PrimaryKey
    private int id;

    private String name;

    private String title;

    private String notice;

    private String text;

    @TypeConverters(DateDataConverter.class)
    private Date date;

    private String metaKeywords;

    @TypeConverters(DateDataConverter.class)
    private Date createdAt;

    private String imageUrl;

    private boolean read;

    public NewsItem() {
        id = -1;
        name = "";
        title = "";
        notice = "";
        text = "";
        date = new Date();
        metaKeywords = "";
        createdAt = new Date();
        imageUrl = "";
        read = false;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @BindingAdapter("bindSmallNewsImage")
    public static void loadSmallImage(final AppCompatImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).resize(200, 200).centerCrop().into(imageView);
        }
    }

    @BindingAdapter("bindBigNewsImage")
    public static void loadBigImage(final AppCompatImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(imageView);
        }
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem item = (NewsItem) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", notice='" + notice + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", metaKeywords='" + metaKeywords + '\'' +
                ", createdAt=" + createdAt +
                ", imageUrl='" + imageUrl + '\'' +
                ", read='" + read + '\'' +
                '}';
    }
}
