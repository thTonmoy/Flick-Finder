package com.tht.movies.utilities;

import com.tht.movies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static Movie[] getMovieInfoFromJson(String data) {
        Movie[] array;
        JSONObject totalData;
        try {
            totalData = new JSONObject(data);
            JSONArray movieArray = totalData.getJSONArray("results");
            int array_length = movieArray.length();
            array = new Movie[array_length];
            for (int i = 0; i < array_length; i++) {
                JSONObject obj = movieArray.getJSONObject(i);
                array[i] = getMovieObjectFromJsonObject(obj);
                //Log.v(TAG + "SIZE = " + array_length + " i = " + i, "OK");

            }
            return array;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Movie getMovieObjectFromJsonObject(JSONObject object) throws JSONException {
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
        poster_path = object.getString("poster_path");
        backdrop_path = object.getString("backdrop_path");


        //Log.v(TAG + "GOT MOVIE", title);
        return new Movie(id, vote_average, title, language, overview, release_date, popularity, poster_path, backdrop_path);
    }

    public static Movie[] getTvInfoFromJson(String data) {
        Movie[] array;
        JSONObject totalData;
        try {
            totalData = new JSONObject(data);
            JSONArray movieArray = totalData.getJSONArray("results");
            int array_length = movieArray.length();
            array = new Movie[array_length];
            for (int i = 0; i < array_length; i++) {
                JSONObject obj = movieArray.getJSONObject(i);
                array[i] = getTvObjectFromJsonObject(obj);
                //Log.v(TAG + "SIZE = " + array_length + " i = " + i, "OK");

            }
            return array;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Movie getTvObjectFromJsonObject(JSONObject object) throws JSONException {
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
        poster_path = object.getString("poster_path");
        backdrop_path = object.getString("backdrop_path");


        //Log.v(TAG + "GOT MOVIE", title);
        return new Movie(id, vote_average, title, language, overview, release_date, popularity, poster_path, backdrop_path);
    }
}
