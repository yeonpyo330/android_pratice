package com.example.savemoneyproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM HISTORY")
    LiveData<List<History>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(History history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);
}
