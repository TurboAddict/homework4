package com.example.maxru.newsapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by maxru on 7/24/17.
 */

public class NewsJob extends JobService {
    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "News refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                RefreshTasks.refresh(NewsJob.this, "bbc-news");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                Log.d("asd", "as");
                super.onPostExecute(o);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mBackgroundTask != null)
            mBackgroundTask.cancel(false);
        return false;
    }
}
