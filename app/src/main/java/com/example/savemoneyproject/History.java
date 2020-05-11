package com.example.savemoneyproject;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_table")
public class History {

    @PrimaryKey(autoGenerate = true)
    private int number;
    @ColumnInfo(name = "history")
    private String history;



    public History(String history) {
        this.history = history;

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }


}
