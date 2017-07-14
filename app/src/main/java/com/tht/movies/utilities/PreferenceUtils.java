package com.tht.movies.utilities;

import com.firebase.jobdispatcher.Constraint;
import com.tht.movies.sync.SyncUtils;

public class PreferenceUtils {

    public static void setSyncOnlyonWifi() {
        SyncUtils.setAppSyncConstraint(Constraint.ON_UNMETERED_NETWORK);

    }

    public static void setSyncInterval(int hour) {
        SyncUtils.setAppSyncConstraint(hour);

    }

    public static synchronized void setImageQuality(String imageQuality) {
        JsonUtils.imageQuality = imageQuality;
    }
}
