package com.d2.pcu.fragments.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.fragments.BaseViewModel;

import java.util.Collections;
import java.util.List;

public class NewsViewModel extends BaseViewModel {

    private Repository repository;

    private LiveData<List<NewsItem>> newsLiveData;
    private int selectedItem;

    public NewsViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        newsLiveData = Transformations.switchMap(
                repository.getTransport().getNewsChannel(), input -> {

                    Collections.sort(
                            input,
                            (o1, o2) -> Long.compare(o2.getDate().getTime(), o1.getDate().getTime())
                    );

                    return new MutableLiveData<>(input);
                }
        );

    }

    public LiveData<List<NewsItem>> getNewsLiveData() {
        return newsLiveData;
    }

    public void loadNews() {
        repository.getNews(100);
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
        if (newsLiveData.getValue() != null) {
            NewsItem item = newsLiveData.getValue().get(selectedItem);
            item.setRead(true);
            repository.updateNewsItemAsRead(item);
        }
    }

    public int getSelectedItem() {
        return selectedItem;
    }

}
