package com.example.lancer.notepad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntegerRes;

import com.example.lancer.notepad.dao.RememberDao;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static DBHelper mDbHelper;
    private Dao<RememberDao, Integer> mRememberDao;

    public static DBHelper getIstance(Context context) {
        if (mDbHelper == null) {
            synchronized (DBHelper.class) {
                if (mDbHelper == null) {
                    mDbHelper = new DBHelper(context, "remember.db", null, 1);
                }
            }
        }
        return mDbHelper;
    }

    public Dao<RememberDao, Integer> getRememberDao(Context context) {
        if (mRememberDao == null) {
            synchronized (DBHelper.class) {
                if (mRememberDao == null) {
                    try {
                        mRememberDao = getDao(RememberDao.class);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mRememberDao;
    }


    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RememberDao.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
