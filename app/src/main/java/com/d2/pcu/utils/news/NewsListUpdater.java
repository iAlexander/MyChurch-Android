package com.d2.pcu.utils.news;

import com.d2.pcu.data.model.news.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsListUpdater {

    public List<NewsItem> updateNewsList(List<NewsItem> oldItems, List<NewsItem> newItems) {

        if (oldItems != null && !oldItems.isEmpty()) {

            List<NewsItem> diffItems = new ArrayList<>(newItems);
            diffItems.removeAll(oldItems);

            if (!diffItems.isEmpty()) {
                int end = oldItems.size() - diffItems.size();
                oldItems = oldItems.subList(0, end);
                oldItems.addAll(diffItems);
            }
            return oldItems;

        } else {
            return newItems;
        }
    }

}
