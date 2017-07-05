package com.tht.movies.data;

import android.provider.BaseColumns;

public class DbContract {

    public class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie_tv_db";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_VOTE_AVG = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_BACKDROP = "backdrop_path";
    }
}
