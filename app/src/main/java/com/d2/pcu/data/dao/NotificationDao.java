package com.d2.pcu.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.d2.pcu.data.model.profile.NotificationHistoryItem;

import java.util.List;

@Dao
public interface NotificationDao {

    @Query("SELECT * FROM notificationhistoryitem")
    List<NotificationHistoryItem> getAllNotification();

    @Query("SELECT * FROM notificationhistoryitem WHERE id = :id LIMIT 1")
    NotificationHistoryItem getNotification(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NotificationHistoryItem item);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insert(List<NotificationHistoryItem> items);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(NotificationHistoryItem item);

    @Delete
    void delete(NotificationHistoryItem item);

    @Delete
    void delete(List<NotificationHistoryItem> items);
}
