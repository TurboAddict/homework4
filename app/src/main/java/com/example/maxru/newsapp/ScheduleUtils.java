package com.example.maxru.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by maxru on 7/24/17.
 */

public class ScheduleUtils {
    private static final String TAG = "news_job";
    private static final int SCHEDULE_INTERVAL_MINUTES = 0;
    private static final int SYNC_FLEXTIME_SECONDS = 6;

    static void scheduleRefresh(@NonNull final Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job newsJob = dispatcher.newJobBuilder()
                .setService(NewsJob.class)
                .setTag(TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setRecurring(true)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                        SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(newsJob);
    }

}
