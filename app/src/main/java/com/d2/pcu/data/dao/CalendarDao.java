package com.d2.pcu.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.d2.pcu.data.model.calendar.CalendarItem;
import com.d2.pcu.data.model.news.NewsItem;
import com.d2.pcu.data.model.profile.NotificationHistoryItem;

import java.util.List;

@Dao
public interface CalendarDao {

    @Query("SELECT * FROM calendaritem")
    LiveData<List<CalendarItem>> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<CalendarItem> items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(CalendarItem item);

    @Delete
    void delete(CalendarItem item);

    @Delete
    void delete(List<CalendarItem> items);
}
