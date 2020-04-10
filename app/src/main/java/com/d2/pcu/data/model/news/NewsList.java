package com.d2.pcu.data.model.news;

import com.d2.pcu.data.db.MasterDbModel;

import java.util.List;

public class NewsList extends MasterDbModel {

    private List<NewsItem> items;

    public List<NewsItem> getItems() {
        return items;
    }

    public void setItems(List<NewsItem> items) {
        this.items = items;
    }
}
