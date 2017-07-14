package com.tht.movies.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class AppFirebaseJobService extends JobService {

    private AsyncTask<Void, Void, Void> fetchDataTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        fetchDataTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Context context = getApplicationContext();
                ContentSyncTask.SyncData(context);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };

        fetchDataTask.execute();

        return true;

    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (fetchDataTask != null) {
            fetchDataTask.cancel(true);
        }

        return true;
    }
}
