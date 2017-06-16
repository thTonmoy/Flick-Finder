package com.tht.movies.utilities;


import android.net.Uri;
import android.util.Log;

import com.tht.movies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    static final String apiKey = BuildConfig.THE_MOVIEDB_API_KEY;
    final static String QUERY_PARAM_MOVIE = "movie?";
    final static String QUERY_PARAM_TV = "tv?";
    final static String API_PARAM = "api_key=";
    final static String LANGUAGE_PARAM = "language";
    final static String SORT_PARAM = "sort_by";
    final static String language = "en-US";
    final static String sort_mode = "popularity.desc";
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/";

    public static URL getRequestUrl() {
        String uriString = BASE_URL + QUERY_PARAM_MOVIE + API_PARAM + apiKey;

        Uri builtUri = Uri.parse(uriString).buildUpon()
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(SORT_PARAM, sort_mode)
                .build();

        Log.v(TAG, "Built URI " + builtUri);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL getRequestUrlTv() {
        String uriString = BASE_URL + QUERY_PARAM_TV + API_PARAM + apiKey;

        Uri builtUri = Uri.parse(uriString).buildUpon()
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(SORT_PARAM, sort_mode)
                .build();

        Log.v(TAG, "Built URI " + builtUri);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}