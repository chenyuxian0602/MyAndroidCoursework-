package com.example.alphabattle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 陈昱宪 on 2016/12/15.
 */

public class user_DB extends SQLiteOpenHelper {
    private static final String USER_DB_NAME = "user.db";
    private static final String USER_TABLE_NAME = "users";
    private static final int DB_VERSION = 1;


    public user_DB(Context context) {
        super(context, USER_DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + USER_TABLE_NAME
                + " (_id INTERGER PRIMARY KEY, user TEXT, password TEXT, score TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(String user, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("password", password);
        values.put("score", "0");
        long id = db.insert(USER_TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int update(String user, String score) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "user = ?";
        String[] whereArgs = { user };

        ContentValues values = new ContentValues();
        values.put("score", score);

        int rows = db.update(USER_TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return rows;
    }

    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(USER_TABLE_NAME, null, null, null, null, null, null);
    }
}
