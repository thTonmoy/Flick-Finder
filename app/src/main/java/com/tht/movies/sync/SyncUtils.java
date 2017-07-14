package com.tht.movies.sync;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.tht.movies.data.DbContract;

import java.util.concurrent.TimeUnit;

public class SyncUtils {
    private static final String APP_SYNC_TAG = "Movie-sync";
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static int sSyncIntervalHour = 36;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(sSyncIntervalHour);
    private static boolean sInitialized;
    private static int AppSyncConstraint = Constraint.ON_ANY_NETWORK;

    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, AppSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        if (sInitialized) {
            return;
        }
        sInitialized = true;
        scheduleFirebaseJobDispatcherSync(context);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Uri dataUri = DbContract.MovieEntry.CONTENT_URI;
                String[] projectionColumns = {DbContract.MovieEntry._ID};
                Cursor cursor = context.getContentResolver().query(dataUri, projectionColumns, null, null, null);
                if (cursor == null || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }

                if (cursor != null) {
                    cursor.close();
                }

                return null;
            }
        }.execute();

    }

    static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);


        Job syncAppDataJob = jobDispatcher.newJobBuilder()
                .setService(AppFirebaseJobService.class)
                .setTag(APP_SYNC_TAG)
                .addConstraint(AppSyncConstraint)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        jobDispatcher.schedule(syncAppDataJob);
    }

    public static void setAppSyncConstraint(int appSyncConstraint) {
        AppSyncConstraint = appSyncConstraint;
    }

    public static void setsSyncIntervalHour(int sSyncIntervalHour) {
        SyncUtils.sSyncIntervalHour = sSyncIntervalHour;
    }
}
