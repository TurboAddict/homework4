package com.example.maxru.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import static com.example.maxru.newsapp.data.Contract.TABLE_NEWS.*;

/**
 * Created by maxru on 7/24/17.
 */

public class DatabaseUtils {

    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor =  db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_DATE
        );
        return cursor;
    }

    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> results) {

        db.beginTransaction();
        try {
            for (NewsItem item : results) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, item.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, item.getDescription());
                cv.put(COLUMN_NAME_URL, item.getUrl());
                cv.put(COLUMN_NAME_DATE, item.getDate());
                cv.put(COLUMN_NAME_URL_TO_IMAGE, item.getUrlToImage());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}
