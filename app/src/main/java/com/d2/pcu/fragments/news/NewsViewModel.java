package com.d2.pcu.fragments.news;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;
import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.fragments.BaseViewModel;
import com.d2.pcu.listeners.OnBackButtonClickListener;

import java.util.Collections;
import java.util.List;

public class NewsViewModel extends BaseViewModel {

    private OnBackButtonClickListener onBackButtonClickListener;

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

        loadNews();
    }

    public void setOnBackButtonClickListener(OnBackButtonClickListener onBackButtonClickListener) {
        this.onBackButtonClickListener = onBackButtonClickListener;
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

    public void onBackPressed(View view) {
        if (onBackButtonClickListener != null) {
            onBackButtonClickListener.onBackButtonPressed();
        }
    }
}
