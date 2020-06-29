package com.example.savemoneyproject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface HistoryDao {


//    @Query("SELECT * FROM history_table")
//    Flowable<List<History>> getAll();

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Income'")
    Flowable<Integer> getIncomeTotal();

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Income' and date like :month||'%'")
    Flowable<Integer> getMonthlyIncomeTotal(String month);

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Cost' and date like :month||'%'")
    Flowable<Integer> getMonthlyCostTotal(String month);

    @Query("SELECT coalesce(sum(coalesce(money,0)),0) FROM history_table WHERE type = 'Cost'")
    Flowable<Integer> getCostTotal();

    @Query("SELECT * FROM history_table WHERE date = (SELECT strftime('%Y%m%d',date('now')))")
    Flowable<List<History>> getTodayHistory();

    @Query("SELECT * FROM history_table WHERE date = :date")
    Flowable<List<History>> getSelectedDateHistory(String date);

    @Query("SELECT * FROM history_table WHERE type = 'Income' and date like :month||'%'")
    Flowable<List<History>> getSelectedMonthIncomeHistory(String month);

    @Query("SELECT * FROM history_table WHERE type = 'Cost' and date like :month||'%'")
    Flowable<List<History>> getSelectedMonthCostHistory(String month);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertHistory(History history);

    @Query("DELETE FROM history_table")
    void deleteAll();
}
