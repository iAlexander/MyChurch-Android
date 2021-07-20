package com.d2.pcu.data.model.news;

import androidx.room.TypeConverters;

import com.d2.pcu.utils.converters.DateDataConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class NewsWpItem {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("date")
    @Expose
    @TypeConverters(DateDataConverter.class)
    public Date date;
    @SerializedName("date_gmt")
    @Expose
    public String dateGmt;
    @SerializedName("modified")
    @Expose
    public String modified;
    @SerializedName("modified_gmt")
    @Expose
    public String modifiedGmt;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("link")
    @Expose
    public String link;
    @SerializedName("title")
    @Expose
    public Content title;
    @SerializedName("content")
    @Expose
    public Content content;
    @SerializedName("excerpt")
    @Expose
    public Content excerpt;
    @SerializedName("author")
    @Expose
    public Integer author;
    @SerializedName("featured_media")
    @Expose
    public int featuredMedia = 0;
    @SerializedName("yoast_head_json")
    @Expose
    public YoastHeadJson yoastHeadJson;

    public String imageUrl;

    protected class Content {
        @SerializedName("rendered")
        @Expose
        public String rendered;
    }

    public NewsItem toNewsItem() {
        NewsItem item = new NewsItem();
        item.setId(id);
        item.setDate(date);
        item.setTitle(title.rendered);
        item.setText(content.rendered);
        if (yoastHeadJson != null && yoastHeadJson.ogImage != null) {
            for (OgImage image: yoastHeadJson.ogImage) {
                if (image.id == featuredMedia){
                    imageUrl = image.url;
                    break;
                }
            }
        }
        item.setImageUrl(imageUrl);

        return item;
    }

    protected class YoastHeadJson {

        @SerializedName("og_image")
        @Expose
        public List<OgImage> ogImage = null;

    }

    protected class OgImage {

        @SerializedName("width")
        @Expose
        public Integer width;
        @SerializedName("height")
        @Expose
        public Integer height;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("path")
        @Expose
        public String path;
        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("alt")
        @Expose
        public String alt;


    }
}

