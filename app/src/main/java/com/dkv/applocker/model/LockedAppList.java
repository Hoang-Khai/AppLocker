package com.dkv.applocker.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dkv.applocker.helper_for_sql.SQLHelper;

import java.io.Serializable;

public class LockedAppList implements Serializable {
    public static String TABLE_NAME = "LockedAppList";
    public static String COLUMN_NAME_ID = "ID";
    public static String COLUMN_NAME_APP_NAME = "AppName";

    private Context context;

    public LockedAppList(Context context) {
        this.context = context;
    }

    public boolean hasForePackageLocked(String forePackage) {
        boolean result=false;

        //select forePackage from DB, if there is 1 or more record, change result to 1
        SQLHelper dbHelper = new SQLHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(LockedAppList.TABLE_NAME, null, LockedAppList.COLUMN_NAME_APP_NAME + "=" + forePackage, null, null, null, null);

        while (cursor.moveToNext())
            result = true;

        cursor.close();
        db.close();
        dbHelper.close();
        ////////////////////////////////////////////////////////////////////////////////////////////

        return result;
    }
}
