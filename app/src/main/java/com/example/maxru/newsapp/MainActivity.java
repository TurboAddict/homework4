package com.example.maxru.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.example.maxru.newsapp.data.Contract;
import com.example.maxru.newsapp.data.DBHelper;
import com.example.maxru.newsapp.data.DatabaseUtils;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Void> {

    private TextView mNewsTextView;
    private SearchView mSearchView;
    private static RecyclerView rv;
    private DBHelper helper;
    private static Cursor cursor;
    private static SQLiteDatabase db;
    private static NewsAdapter adapter;
    private static final int NEWS_LOADER = 1;
    static final String TAG = "mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsTextView = (TextView) findViewById(R.id.news_data);
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isFirst", true);
        Log.d(TAG, String.valueOf(isFirst));

        //Checks to see if this app has ever been before
        if (isFirst) {
            load("bbc-news"); //Populate some kind of data. In this case, we used bbc-news
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
            Log.d(TAG, String.valueOf(prefs.getBoolean("isFirst", true)));
        }
        //Refreshes after minute
        ScheduleUtils.scheduleRefresh(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search_bar).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                mNewsTextView.setText("");
                loadNewsData(query);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start");

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        cursor = DatabaseUtils.getAll(db);

        adapter = new NewsAdapter(cursor, new NewsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {
                cursor.moveToPosition(clickedItemIndex);
                String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL));
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            }
        });

        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    private void loadNewsData(String source) {
        load(source);
    }

    //Replaced ASyncTask with ASyncTaskLoader
    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            public Void loadInBackground() {
                RefreshTasks.refresh(MainActivity.this, args.getString("source"));
                return null;
            }
        };
    }
    //When load is finished, adapter is set to rv, then the adapter is notified of the changed so it can display the new data
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor, new NewsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {
                        cursor.moveToPosition(clickedItemIndex);
                        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL));
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                        startActivity(intent);
            }
        });
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d(TAG,"down");
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    //Added String source to a bundle so the loader knows where to grab thr articles from
    public void load(String source) {
        Bundle b = new Bundle();
        b.putString("source", source);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, b, this).forceLoad();
    }

    public static void update() {
        cursor = DatabaseUtils.getAll(db);
        adapter = new NewsAdapter(cursor, new NewsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int clickedItemIndex) {
                cursor.moveToPosition(clickedItemIndex);
                String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_NEWS.COLUMN_NAME_URL));
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                //startActivity(intent);
            }
        });
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}