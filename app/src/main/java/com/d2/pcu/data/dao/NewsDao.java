package com.d2.pcu.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.d2.pcu.data.model.news.NewsItem;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM newsitem")
    List<NewsItem> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NewsItem newsItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<NewsItem> items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(NewsItem newsItem);

    @Delete
    void delete(NewsItem newsItem);

    @Delete
    void delete(List<NewsItem> items);
}
