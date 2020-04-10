package com.d2.pcu.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.d2.pcu.App;
import com.d2.pcu.data.db.AppDatabase;
import com.d2.pcu.data.db.OnDbResult;
import com.d2.pcu.data.db.OnDbResultState;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.model.news.NewsList;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.data.model.pray.PraysList;
import com.d2.pcu.utils.news.NewsListUpdater;

import java.util.List;


public class DbLoader implements DefaultLifecycleObserver {

    private static final String TAG = DbLoader.class.getSimpleName();

    private AppDatabase database;

    private Handler handler;

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        database = App.getInstance().getDatabase();

        HandlerThread handlerThread = new HandlerThread("databaseThread");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());

        Log.d(TAG, "Created...");
    }

    void savePray(Pray pray, OnDbResultState resultState) {
        handler.post(() -> {
                    database.prayDao().insert(pray);
                    resultState.onResult(true);
                }
        );
    }

    void getPrayById(final int id, final OnDbResult result) {
        handler.post(() -> {
            Pray pray = database.prayDao().getPrayById(id);

            if (pray != null) {
                result.onSuccess(pray);
            } else {
                result.onFail();
            }
        });
    }

    void savePrays(List<Pray> prays) {
        handler.post(() -> database.prayDao().insertPrays(prays));
    }

    void getPrays(final String type, final OnDbResult result) {
        handler.post(() -> {
            List<Pray> prays = database.prayDao().getAllPrays(type);

            if (prays != null) {
                PraysList praysList = new PraysList();
                praysList.setPrays(prays);

                result.onSuccess(praysList);
            } else {
                result.onFail();
            }
        });
    }

    void removePray(final Pray pray, OnDbResultState resultState) {
        handler.post(() -> {
                    database.prayDao().delete(pray);
                    resultState.onResult(true);

                }
        );
    }

    void getNews(OnDbResult result) {
        handler.post(() -> {
            List<NewsItem> items = database.newsDao().getAllNews();

            if (items != null) {
                NewsList newsList = new NewsList();
                newsList.setItems(items);

                result.onSuccess(newsList);
            } else {
                result.onFail();
            }
         });
    }

    void updateAndSaveNewsToDb(final List<NewsItem> items, OnDbResultState resultState) {
        handler.post(() -> {

            List<NewsItem> oldItems = database.newsDao().getAllNews();

            NewsListUpdater newsListUpdater = new NewsListUpdater();
            List<NewsItem> newItems = newsListUpdater.updateNewsList(oldItems, items);

            List<Long> response = database.newsDao().insert(newItems);
            if (!response.isEmpty()) {
                resultState.onResult(true);
            } else {
                resultState.onResult(false);
            }
        });
    }

    void updateReadNewsItem(NewsItem newsItem, OnDbResultState resultState) {
        handler.post(() -> {
            int result = database.newsDao().update(newsItem);
            if (result >= 1) {
                resultState.onResult(true);
            } else {
                resultState.onResult(false);
            }
        });
    }
}