package com.d2.pcu.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.d2.pcu.data.dao.NewsDao;
import com.d2.pcu.data.dao.NotificationDao;
import com.d2.pcu.data.dao.PrayDao;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.model.pray.Pray;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;

@Database(
        entities = {
                Pray.class,
                NewsItem.class,
                NotificationHistoryItem.class
        },
        version = 2
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PrayDao prayDao();
    public abstract NewsDao newsDao();
    public abstract NotificationDao notificationDao();

}


