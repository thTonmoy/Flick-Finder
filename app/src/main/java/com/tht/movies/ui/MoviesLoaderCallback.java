package com.tht.movies.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tht.movies.data.DbContract;
import com.tht.movies.utilities.TmbdUtils;

class MoviesLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

    private MovieTvAdapter mMoviedapter;
    private RecyclerView mRecyclerView;
    private TextView ErrorTextView;
    private Context context;
    private int type;

    MoviesLoaderCallback(int type, TextView ErrorTextView, MovieTvAdapter mMoviedapter, RecyclerView mRecyclerView, Context context) {
        this.type = type;
        this.ErrorTextView = ErrorTextView;
        this.mMoviedapter = mMoviedapter;
        this.mRecyclerView = mRecyclerView;
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri allContentUri = DbContract.MovieEntry.CONTENT_URI;
        //String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";

        String selection = DbContract.MovieEntry.COLUMN_TYPE_MOVIE_OR_TV + " =? ";
        switch (type) {
            case TmbdUtils.CONTENT_TYPE_MOVIE:
                String typeString = String.valueOf(TmbdUtils.CONTENT_TYPE_MOVIE);
                String[] selectionArgs = new String[]{typeString};
                return new CursorLoader(context,
                        allContentUri,
                        MainActivity.MAIN_CONTENT_PROJECTION,
                        selection,
                        selectionArgs,
                        null);
            case TmbdUtils.CONTENT_TYPE_TV:
                String typeStringTv = String.valueOf(TmbdUtils.CONTENT_TYPE_TV);
                String[] selectionArgsTv = new String[]{typeStringTv};
                return new CursorLoader(context,
                        allContentUri,
                        MainActivity.MAIN_CONTENT_PROJECTION,
                        selection,
                        selectionArgsTv,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: ");
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Log.v("On Load Finished", "F u");
        mMoviedapter.setMovieData(data);
        if (data != null) {

            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            ErrorTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
