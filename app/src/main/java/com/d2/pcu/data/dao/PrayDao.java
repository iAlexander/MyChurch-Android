package com.d2.pcu.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.d2.pcu.data.model.pray.Pray;

import java.util.List;

@Dao
public interface PrayDao {

    @Query("SELECT * FROM pray")
    List<Pray> getAllPrays();

    @Query("SELECT * FROM pray WHERE type=:type")
    LiveData<List<Pray>> getAllPraysLiveData(String type);

    @Query("SELECT * FROM pray WHERE type=:type")
    List<Pray> getAllPrays(String type);

    @Query("SELECT * FROM pray where id=:id")
    Pray getPrayById(int id);

    @Insert
    void insertPrays(List<Pray> prays);

    @Insert
    void insert(Pray pray);

    @Delete
    void delete(Pray pray);
}
