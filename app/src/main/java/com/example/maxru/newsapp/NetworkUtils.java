package com.example.maxru.newsapp;

import android.net.Uri;

import com.example.maxru.newsapp.data.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by maxru on 6/21/17.
 */

public class NetworkUtils {
    private static final String BASE_URL = "https://newsapi.org/v1/articles";
    private static final String QUERY_PARAM = "source";
    private static final String API_KEY_PARAM = "apiKey";

    private final static String apiKey = "247e70c8b5624592a9f23df7512f18b4";

    public static URL buildUrl(String Query) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, Query)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> results = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("articles");

        //Instead of grabbing the values from JSON, we grab it from the table
        for(int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            results.add(new NewsItem(item.getString("title"), item.getString("description"), item.getString("url"), item.getString("publishedAt"), item.getString("urlToImage")));
        }
        return results;
    }
}
