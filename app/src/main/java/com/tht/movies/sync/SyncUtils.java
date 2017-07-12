package com.tht.movies.sync;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class SyncUtils {

    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, AppSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}
