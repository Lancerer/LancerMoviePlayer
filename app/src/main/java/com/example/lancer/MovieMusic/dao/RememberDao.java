package com.example.lancer.MovieMusic.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "jizhang")
public class RememberDao {
    @DatabaseField(columnName = "money")
    private int money;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "all")
    private int all;
    @DatabaseField(columnName = "time")
    private String time;

    public RememberDao() {

    }
    public RememberDao(int money, String type, int all, String time) {

        this.money = money;
        this.type = type;
        this.all = all;
        this.time = time;
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

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
