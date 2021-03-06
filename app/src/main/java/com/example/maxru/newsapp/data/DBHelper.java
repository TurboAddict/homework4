package com.example.maxru.newsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by maxru on 7/24/17.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String TAG = "dbhelper";
    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    //Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_NEWS.TABLE_NAME + " (" +
                Contract.TABLE_NEWS._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contract.TABLE_NEWS.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_NEWS.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_NEWS.COLUMN_NAME_DATE + " DATE, " +
                Contract.TABLE_NEWS.COLUMN_NAME_URL + " TEXT NOT NULL, " +
                Contract.TABLE_NEWS.COLUMN_NAME_URL_TO_IMAGE + " TEXT NOT NULL" + "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
