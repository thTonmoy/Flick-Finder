package com.tht.movies.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class AppSyncIntentService extends IntentService {

    public AppSyncIntentService() {
        super("FFSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ContentSyncTask.SyncData(this);
        Log.v("Sync requested", " ");

    }
}
