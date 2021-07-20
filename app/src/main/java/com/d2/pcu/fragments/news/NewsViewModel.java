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
    private final int MAX = 100;
    private int page = 1;
    private int perPage = 10;
    private boolean isLoading = false;

    public NewsViewModel() {
        repository = App.getInstance().getRepositoryInstance();

        newsLiveData = Transformations.switchMap(
                repository.getTransport().getNewsChannel(), input -> {

                    Collections.sort(
                            input,
                            (o1, o2) -> Long.compare(o2.getDate().getTime(), o1.getDate().getTime())
                    );
                    isLoading = false;

                    return new MutableLiveData<>(input);
                }
        );

    }

    public LiveData<List<NewsItem>> getNewsLiveData() {
        return newsLiveData;
    }

    public void loadNewsForce() {
        if (isLoading) return;
        isLoading = true;
        page = 1;
        repository.getNews(page);
    }

    public void loadNews() {
        if(isLoading || page*perPage == MAX) return;
        isLoading = true;
        page++;
        repository.getNews(page);
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
