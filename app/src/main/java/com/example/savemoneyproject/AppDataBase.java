package com.example.savemoneyproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {History.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract HistoryDao historyDao();
    private static AppDataBase INSTANCE;

    static AppDataBase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDataBase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "history_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}