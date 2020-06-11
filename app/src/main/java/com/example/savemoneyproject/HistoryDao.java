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

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Income'")
    LiveData<Integer> getIncomeTotal();

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Income' and date like :month||'%'")
    LiveData<Integer> getMonthlyIncomeTotal(String month);

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Cost' and date like :month||'%'")
    LiveData<Integer> getMonthlyCostTotal(String month);

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Cost'")
    LiveData<Integer> getCostTotal();

    @Query("SELECT * FROM history_table WHERE date = (SELECT strftime('%Y%m%d',date('now')))")
    LiveData<List<History>> getTodayHistory();

    @Query("SELECT * FROM history_table WHERE date = :date")
    LiveData<List<History>> getSelectedDateHistory(String date);

    @Query("SELECT * FROM history_table WHERE type = 'Income' and date like :month||'%'")
    LiveData<List<History>> getSelectedMonthIncomeHistory(String month);

    @Query("SELECT * FROM history_table WHERE type = 'Cost' and date like :month||'%'")
    LiveData<List<History>> getSelectedMonthCostHistory(String month);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(History history);

    @Query("DELETE FROM history_table")
    void deleteAll();
}
