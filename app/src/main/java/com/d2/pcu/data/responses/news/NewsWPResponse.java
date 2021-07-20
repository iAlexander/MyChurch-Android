package com.d2.pcu.data.responses.news;

import com.d2.pcu.data.model.news.NewsWpItem;
import com.d2.pcu.data.responses.OnMasterResponse;

import java.util.List;

public class NewsWPResponse extends OnMasterResponse {

    private List<NewsWpItem> news;

    public List<NewsWpItem> getNews() {
        return news;
    }

    public void setNews(List<NewsWpItem> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "NewsWPResponse{" +
                "news=" + news +
                '}';
    }
}
