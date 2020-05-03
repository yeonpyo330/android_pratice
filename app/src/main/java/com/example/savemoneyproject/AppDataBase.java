package com.example.savemoneyproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {History.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract HistoryDao historyDao();

}
