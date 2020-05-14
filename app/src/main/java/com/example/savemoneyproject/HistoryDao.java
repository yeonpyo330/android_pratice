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

    //TODO : Can not work yet
//    @Query("SELECT COALESCE(SUM(COALESCE(money,0)),0) FROM history_table WHERE type='Income'")
//    LiveData<List<History>> getIncomeTotal();
//
//    @Query("SELECT COALESCE(SUM(COALESCE(money,0)),0) FROM history_table WHERE type='Cost'")
//    LiveData<List<History>> getCostTotal();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(History history);

    @Query("DELETE FROM history_table")
    void deleteAll();
}
