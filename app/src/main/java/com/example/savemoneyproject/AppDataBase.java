package com.example.savemoneyproject;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {History.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract HistoryDao historyDao();
    private static AppDataBase INSTANCE;

//    static final Migration MIGRATION_1_ADD_MONEY_TYPE_TO_MARKETS = new Migration(1,2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("alter table history_table add column money INTEGER");
//            database.execSQL("alter table history_table add column type real");
//        }
//    };

    static AppDataBase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDataBase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "history_database")
                            .fallbackToDestructiveMigration()
//                            .addMigrations()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}