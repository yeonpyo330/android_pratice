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

//    @Query("SELECT SUM(money) FROM history_table WHERE type = 'Income'")
//    Integer getIncomeTotal();
//
//    @Query("SELECT SUM(money) FROM history_table WHERE type = 'Cost'")
//    Integer getCostTotal();

    @Query("SELECT * FROM history_table WHERE date = (SELECT strftime('%Y%m%d',date('now')))")
    LiveData<List<History>> getTodayHistory();

    @Query("SELECT * FROM history_table WHERE date = :date")
    LiveData<List<History>> getSelectedDateHistory(String date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(History history);

    @Query("DELETE FROM history_table")
    void deleteAll();
}
