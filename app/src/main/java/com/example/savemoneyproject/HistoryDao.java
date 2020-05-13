package com.example.savemoneyproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM history_table")
    LiveData<List<History>> getAll();

    @Query("SELECT money FROM history_table ")
    List<History> getMoneyTotal();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(History history);

    @Query("DELETE FROM history_table")
    void deleteAll();
}
