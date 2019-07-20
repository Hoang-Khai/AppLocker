package com.dkv.applocker.model;

import com.dkv.applocker.exception.WrongPasswordException;
import com.dkv.applocker.helper_for_sql.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;

public class Password implements Serializable {
    public static String TABLE_NAME = "Password";
    public static String COLUMN_NAME_TOKEN = "Token";

    private Context context;
    private String password;

    public Password(String password, Context context) {
        this.password = password;
        this.context = context;
    }

    public Password(Context applicationContext) {
        context = applicationContext;
    }

    public boolean hasPasswordInDB() {
        return !(getTokenFromDB()==null);
    }

    public void changePasswordInSQlite(String oldPassword) throws WrongPasswordException {
        //for safeness, save HASHCODE to DB, not the password.
        String newToken = "" + password.hashCode();

        String oldToken = getTokenFromDB();
        //If there is a token in db
        if(oldToken!=null) {
            //check if oldPassword matches the token in DB
            if(isMatchedWithTokenInDB(oldPassword)) {
                //delete all record and write the new password token
                writeTokenToDB(newToken);
            } else {
                //throw exception failed to login
                throw new WrongPasswordException();
            }
        } else {
            //delete all record and write the new password token
            writeTokenToDB(newToken);
        }
    }

    public void changePasswordInSQlite() {
        try {
            changePasswordInSQlite(null);
        } catch (WrongPasswordException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isMatchedWithTokenInDB(String inputPassword) {
        boolean result = false;
        String oldToken = getTokenFromDB();
        if(oldToken.equals(""+inputPassword.hashCode())) {
            result = true;
            Log.i("Login","Password OK");
        } else {
            Log.i("Login","Password wrong\n" + oldToken + "\n"+("1234".hashCode()) + "\n"+(inputPassword.hashCode()));
        }
        return result;
    }
    
    public boolean isMatchedWithTokenInDB() {
        return isMatchedWithTokenInDB(password);
    }

    private void writeTokenToDB(String newToken) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Code to write token in here
        SQLHelper dbHelper = new SQLHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(Password.TABLE_NAME,null,null);
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TOKEN,newToken);
        db.insert(Password.TABLE_NAME,null,values);

        db.close();
        dbHelper.close();
        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    private String getTokenFromDB() {
        String token = null;

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Get token from DB
        SQLHelper dbHelper = new SQLHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Password.TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            token = cursor.getString(cursor.getColumnIndexOrThrow(Password.COLUMN_NAME_TOKEN));
        }

        cursor.close();
        db.close();
        dbHelper.close();
        ////////////////////////////////////////////////////////////////////////////////////////////

        return token;
    }

    //for debug only
    public void rsPassword(String newPassword) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Code to write token in here
        SQLHelper dbHelper = new SQLHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(Password.TABLE_NAME,null,null);
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TOKEN,newPassword.hashCode());
        db.insert(Password.TABLE_NAME,null,values);

        db.close();
        dbHelper.close();
        ////////////////////////////////////////////////////////////////////////////////////////////
    }
    public void delPassword() {
        ////////////////////////////////////////////////////////////////////////////////////////////
        SQLHelper dbHelper = new SQLHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(Password.TABLE_NAME,null,null);

        db.close();
        dbHelper.close();
        ////////////////////////////////////////////////////////////////////////////////////////////
    }
}
