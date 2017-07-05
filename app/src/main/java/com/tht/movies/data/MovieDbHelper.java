package com.tht.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tht.movies.data.DbContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +

                        MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_TITLE + "TEXT NOT NULL, " +
                        MovieEntry.COLUMN_LANGUAGE + "TEXT NOT NULL, " +
                        MovieEntry.COLUMN_OVERVIEW + "TEXT NOT NULL, " +
                        MovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL, " +
                        MovieEntry.COLUMN_VOTE_AVG + "REAL NOT NULL, " +
                        MovieEntry.COLUMN_POPULARITY + "REAL NOT NULL, " +
                        MovieEntry.COLUMN_POSTER + "TEXT NOT NULL, " +
                        MovieEntry.COLUMN_BACKDROP + "TEXT NOT NULL, " +
                        " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
