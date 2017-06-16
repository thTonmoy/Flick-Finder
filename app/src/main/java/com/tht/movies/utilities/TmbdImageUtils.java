package com.tht.movies.utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class TmbdImageUtils {

    private static final String BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String SIZE = "w342/";

    public static URL createImageUrl(String s) {
        String temp = BASE_URL + SIZE + s;
        try {
            Uri uri = Uri.parse(temp);
            URL url = new URL(uri.toString());
            Log.v("Image Utils: Created", uri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createImageUrl_P(String s) {
        String temp = BASE_URL + SIZE + s;
        Uri uri = Uri.parse(temp);
        //URL url = new URL(uri.toString());
        Log.v("Image Utils: Created", uri.toString());
        return uri.toString();
    }

}
