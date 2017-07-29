package com.example.maxru.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.maxru.newsapp.data.DBHelper;
import com.example.maxru.newsapp.data.DatabaseUtils;
import com.example.maxru.newsapp.data.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by maxru on 7/24/17.
 */

public class RefreshTasks {

    public static void refresh(Context context, String source) {
        ArrayList<NewsItem> results = null;
        URL url = NetworkUtils.buildUrl(source);
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            results = NetworkUtils.parseJSON(json);
            DatabaseUtils.bulkInsert(db, results);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
