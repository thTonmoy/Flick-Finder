package com.tht.movies.utilities;

import android.net.Uri;

public class TmbdImageUtils {

    private static final String BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String SIZE = "w342/";

    public static String createImageUrl_P(String s) {
        String temp = BASE_URL + SIZE + s;
        Uri uri = Uri.parse(temp);
        //Log.v("Image Utils: Created", uri.toString());
        return uri.toString();
    }

}
