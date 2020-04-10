package com.d2.pcu.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.d2.pcu.data.dao.NewsDao;
import com.d2.pcu.data.dao.PrayDao;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.model.pray.Pray;

@Database(
        entities = {
                Pray.class,
                NewsItem.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PrayDao prayDao();
    public abstract NewsDao newsDao();
}
