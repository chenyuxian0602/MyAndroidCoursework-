package com.example.ex8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 陈昱宪 on 2016/11/22.
 */

public class myDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;

    public myDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + " (_id INTERGER PRIMARY KEY, name TEXT,birth TEXT,gift TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(info entity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", entity.getName());
        values.put("birth", entity.getBirthday());
        values.put("gift", entity.getGift());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int update(info entity) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name = ?";
        String[] whereArgs = { entity.getName() };

        ContentValues values = new ContentValues();
        values.put("name", entity.getName());
        values.put("birth", entity.getBirthday());
        values.put("gift", entity.getGift());

        int rows = db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return rows;
    }

    public int delete(info entity) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name = ?";
        String[] whereArgs = { entity.getName() };

        int rows = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rows;
    }

    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
