package com.tht.movies.utilities;

import android.content.ContentValues;

import com.tht.movies.data.DbContract.MovieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    private static String imageQuality;

    public static synchronized void setImageQuality(String imageQuality) {
        JsonUtils.imageQuality = imageQuality;
    }

    public static ContentValues[] getMovieInfoFromJson(String data) {
        ContentValues[] values;
        JSONObject totalData;
        try {
            totalData = new JSONObject(data);
            JSONArray movieArray = totalData.getJSONArray("results");
            int array_length = movieArray.length();
            values = new ContentValues[array_length];
            for (int i = 0; i < array_length; i++) {
                JSONObject obj = movieArray.getJSONObject(i);
                values[i] = getContentValueFromJsonObject(obj);
                //Log.v(TAG + "SIZE = " + array_length + " i = " + i, "OK");

            }
            return values;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ContentValues getContentValueFromJsonObject(JSONObject object) throws JSONException {
        ContentValues content = new ContentValues();

        int id;
        double vote_average;
        String title, language, overview, release_date;
        double popularity;
        String poster_path, backdrop_path;
        id = object.getInt("id");
        vote_average = object.getDouble("vote_average");
        title = object.getString("original_title");
        language = object.getString("original_language");
        overview = object.getString("overview");
        release_date = object.getString("release_date");
        popularity = object.getDouble("popularity");
        poster_path = TmbdUtils.createImageUrl_P(imageQuality, object.getString("poster_path"));
        backdrop_path = TmbdUtils.createImageUrl_P(imageQuality, object.getString("backdrop_path"));

        content.put(MovieEntry.COLUMN_MOVIE_ID, String.valueOf(id));
        content.put(MovieEntry.COLUMN_TYPE_MOVIE_OR_TV, TmbdUtils.CONTENT_TYPE_MOVIE);
        content.put(MovieEntry.COLUMN_VOTE_AVG, vote_average);
        content.put(MovieEntry.COLUMN_TITLE, title);
        content.put(MovieEntry.COLUMN_LANGUAGE, language);
        content.put(MovieEntry.COLUMN_OVERVIEW, overview);
        content.put(MovieEntry.COLUMN_RELEASE_DATE, release_date);
        content.put(MovieEntry.COLUMN_POPULARITY, popularity);
        content.put(MovieEntry.COLUMN_POSTER, poster_path);
        content.put(MovieEntry.COLUMN_BACKDROP, backdrop_path);

        return content;

    }

    public static ContentValues[] getTvInfoFromJson(String data) {
        ContentValues[] values;
        JSONObject totalData;
        try {
            totalData = new JSONObject(data);
            JSONArray movieArray = totalData.getJSONArray("results");
            int array_length = movieArray.length();
            values = new ContentValues[array_length];
            for (int i = 0; i < array_length; i++) {
                JSONObject obj = movieArray.getJSONObject(i);
                values[i] = getTvObjectFromJsonObject(obj);
                //Log.v(TAG + "SIZE = " + array_length + " i = " + i, "OK");

            }
            return values;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ContentValues getTvObjectFromJsonObject(JSONObject object) throws JSONException {
        ContentValues content = new ContentValues();

        int id;
        double vote_average;
        String title, language, overview, release_date;
        double popularity;
        String poster_path, backdrop_path;
        id = object.getInt("id");
        vote_average = object.getDouble("vote_average");
        title = object.getString("original_name");
        language = object.getString("original_language");
        overview = object.getString("overview");
        release_date = object.getString("first_air_date");
        popularity = object.getDouble("popularity");
        poster_path = TmbdUtils.createImageUrl_P(imageQuality, object.getString("poster_path"));
        backdrop_path = TmbdUtils.createImageUrl_P(imageQuality, object.getString("backdrop_path"));

        content.put(MovieEntry.COLUMN_MOVIE_ID, String.valueOf(id));
        content.put(MovieEntry.COLUMN_TYPE_MOVIE_OR_TV, TmbdUtils.CONTENT_TYPE_TV);
        content.put(MovieEntry.COLUMN_VOTE_AVG, vote_average);
        content.put(MovieEntry.COLUMN_TITLE, title);
        content.put(MovieEntry.COLUMN_LANGUAGE, language);
        content.put(MovieEntry.COLUMN_OVERVIEW, overview);
        content.put(MovieEntry.COLUMN_RELEASE_DATE, release_date);
        content.put(MovieEntry.COLUMN_POPULARITY, popularity);
        content.put(MovieEntry.COLUMN_POSTER, poster_path);
        content.put(MovieEntry.COLUMN_BACKDROP, backdrop_path);

        return content;
    }
}
