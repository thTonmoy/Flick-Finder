package com.tht.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    public static final String CONTENT_AUTHORITY = "com.tht.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES_TV = "movies_and_tv";

    public static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES_TV)
                .build();

        public static final String TABLE_NAME = "movie_tv_db";
        public static final String COLUMN_TYPE_MOVIE_OR_TV = "movie_or_tv";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_VOTE_AVG = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_BACKDROP = "backdrop_path";

        public static Uri buildContentUriWithId(String id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(id)
                    .build();
        }
    }
}
