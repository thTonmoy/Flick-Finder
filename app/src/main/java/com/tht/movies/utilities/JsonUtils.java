package com.tht.movies.utilities;

import android.content.ContentValues;

import com.tht.movies.data.DbContract.MovieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    static String imageQuality;
    private static ArrayList<ContentValues> values;

    public static ContentValues[] getContentValuesFromJson(String Moviedata, String Tvdata) {

        values = new ArrayList<>();

        getMovieInfoFromJson(Moviedata);
        getTvInfoFromJson(Tvdata);

        ContentValues[] finalValues = new ContentValues[values.size()];
        finalValues = values.toArray(finalValues);
        return finalValues;
    }

    private static void getMovieInfoFromJson(String data) {
        JSONObject totalData;
        try {
            totalData = new JSONObject(data);
            JSONArray movieArray = totalData.getJSONArray("results");
            int array_length = movieArray.length();
            for (int i = 0; i < array_length; i++) {
                JSONObject obj = movieArray.getJSONObject(i);
                values.add(getContentValueFromJsonObject(obj));

            }

        } catch (JSONException e) {
            e.printStackTrace();
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

    private static void getTvInfoFromJson(String data) {
        JSONObject totalData;
        try {
            totalData = new JSONObject(data);
            JSONArray movieArray = totalData.getJSONArray("results");
            int array_length = movieArray.length();
            for (int i = 0; i < array_length; i++) {
                JSONObject obj = movieArray.getJSONObject(i);
                values.add(getTvObjectFromJsonObject(obj));
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
