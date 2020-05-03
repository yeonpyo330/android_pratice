package com.example.savemoneyproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM HISTORY")
    List<History> getAll();

    @Insert
    void insert(History history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);
}
