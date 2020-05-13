package com.example.savemoneyproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history_table")
public class History {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "money")
    private int money;
    @ColumnInfo(name = "history")
    private String history;
    @ColumnInfo(name = "type")
    private String type;

    public History(String history, int money, String type) {
        this.history = history;
        this.money = money;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

