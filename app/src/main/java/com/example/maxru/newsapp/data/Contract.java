package com.example.maxru.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by maxru on 7/24/17.
 */

public class Contract {
    public static class TABLE_NEWS implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_URL_TO_IMAGE = "urlToImage";
    }
}
