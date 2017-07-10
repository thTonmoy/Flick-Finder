package com.tht.movies.ui;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tht.movies.R;
import com.tht.movies.model.Movie;
import com.tht.movies.utilities.JsonUtils;
import com.tht.movies.utilities.NetworkUtils;
import com.tht.movies.utilities.TmbdUtils;

import java.io.IOException;

class MoviesLoaderCallback implements LoaderManager.LoaderCallbacks<Movie[]> {

    private ProgressBar mProgressBar;
    private TextView errorTextView;
    private SharedPreferences preference;
    private MovieTvAdapter mMoviedapter;
    private RecyclerView mRecyclerView;
    private Context context;
    private String type;

    MoviesLoaderCallback(String type, ProgressBar mProgressBar, TextView errorTextView, SharedPreferences preference, MovieTvAdapter mMoviedapter, RecyclerView mRecyclerView, Context context) {
        this.type = type;
        this.mProgressBar = mProgressBar;
        this.errorTextView = errorTextView;
        this.preference = preference;
        this.mMoviedapter = mMoviedapter;
        this.mRecyclerView = mRecyclerView;
        this.context = context;
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Movie[]>(context) {
            Movie[] mMovieDataArray;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mMovieDataArray == null) {
                    errorTextView.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                } else {
                    deliverResult(mMovieDataArray);
                }
            }

            @Override
            public Movie[] loadInBackground() {
                try {
                    String sortingParam = preference.getString(context.getString(R.string.pref_sort_key), context.getString(R.string.pref_sort_popularity));
                    String ImageQuality = preference.getString(context.getString(R.string.pref_image_quality_key), context.getString(R.string.pref_quality_medium));
                    JsonUtils.setImageQuality(ImageQuality);
                    switch (type) {
                        case TmbdUtils.CONTENT_TYPE_MOVIE:
                            String s = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getRequestUrl(sortingParam));
                            mMovieDataArray = JsonUtils.getMovieInfoFromJson(s);
                            break;
                        case TmbdUtils.CONTENT_TYPE_TV:
                            String tvstring = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getRequestUrlTv(sortingParam));
                            mMovieDataArray = JsonUtils.getTvInfoFromJson(tvstring);
                            break;
                        default:
                            Log.v("LoaderCallback ", " invalid type");
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return mMovieDataArray;

            }

            @Override
            public void deliverResult(Movie[] data) {
                mMovieDataArray = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        //Log.v("On Load Finished", "F u");
        mProgressBar.setVisibility(View.INVISIBLE);
        mMoviedapter.setMovieData(data);
        if (data != null) {

            showDataView();
            //Log.v("Reached finish", " l = 0");
        } else {
            errorTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {

    }

    private void showDataView() {
        mProgressBar.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
