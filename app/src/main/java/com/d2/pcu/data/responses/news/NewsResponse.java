package com.d2.pcu.data.responses.news;

import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.responses.OnMasterResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse extends OnMasterResponse {

    @SerializedName("data")
    private List<NewsItem> news;

    public List<NewsItem> getNews() {
        return news;
    }

    public void setNews(List<NewsItem> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "news=" + news +
                '}';
    }
}
