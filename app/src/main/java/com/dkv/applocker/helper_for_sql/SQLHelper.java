package com.dkv.applocker.helper_for_sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dkv.applocker.model.LockedAppList;
import com.dkv.applocker.model.Password;

public class SQLHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppLock.db";
    private static final String SQL_CREATE_PASSWORD_TABLE_ENTRIES = "CREATE TABLE " + Password.TABLE_NAME + " (" + Password.COLUMN_NAME_TOKEN +" TEXT)";
    private static final String SQL_CREATE_APPLIST_TABLE_ENTRIES = "CREATE TABLE " + LockedAppList.TABLE_NAME + " (" + LockedAppList.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "+ LockedAppList.COLUMN_NAME_APP_NAME + " TEXT)";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PASSWORD_TABLE_ENTRIES);
        db.execSQL(SQL_CREATE_APPLIST_TABLE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
