package com.tht.movies.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.tht.movies.R;
import com.tht.movies.data.DbContract;
import com.tht.movies.utilities.JsonUtils;
import com.tht.movies.utilities.NetworkUtils;
import com.tht.movies.utilities.PreferenceUtils;

import java.io.IOException;

public class ContentSyncTask {

    private static ContentValues[] mMovieDataArray;
    private static SharedPreferences preference;

    public static void SyncData(Context context) {
        try {
            preference = PreferenceManager.getDefaultSharedPreferences(context);
            String sortingParam = preference.getString(context.getString(R.string.pref_sort_key), context.getString(R.string.pref_sort_popularity));
            String ImageQuality = preference.getString(context.getString(R.string.pref_image_quality_key), context.getString(R.string.pref_quality_medium));
            PreferenceUtils.setImageQuality(ImageQuality);
            String movieString = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getRequestUrl(sortingParam));
            String tvstring = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getRequestUrlTv(sortingParam));
            mMovieDataArray = JsonUtils.getContentValuesFromJson(movieString, tvstring);
            useBulkinsert(context, mMovieDataArray);

            //Log.v("SyncTask ", " invalid type");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void useBulkinsert(Context context, ContentValues[] mMovieDataArray) {

        if (mMovieDataArray != null && mMovieDataArray.length != 0) {

            ContentResolver contentResolver = context.getContentResolver();

            contentResolver.delete(
                    DbContract.MovieEntry.CONTENT_URI,
                    null,
                    null);

            contentResolver.bulkInsert(
                    DbContract.MovieEntry.CONTENT_URI,
                    mMovieDataArray);
        }
    }
}
