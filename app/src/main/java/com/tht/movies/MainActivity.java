package com.tht.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tht.movies.data.Movie;
import com.tht.movies.utilities.JsonUtils;
import com.tht.movies.utilities.NetworkUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Movie[]>, MoviesAdapter.MoviesAdapterOnClickHandler {

    static final int LOADER_ID = 116;
    ProgressBar mProgressBar;
    RecyclerView mRecyclerView;
    TextView errorTextView;
    MoviesAdapter mMoviedapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        errorTextView = (TextView) findViewById(R.id.tv_error_message_display);
        errorTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mRecyclerView.setVisibility(View.INVISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviedapter = new MoviesAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMoviedapter);
        mRecyclerView.setHasFixedSize(true);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Movie[]>(this) {
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
                    String s = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getRequestUrl());
                    mMovieDataArray = JsonUtils.getMovieInfoFromJson(s);
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

    void showDataView() {
        mProgressBar.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("parcelable_extra", movie);
        startActivity(intentToStartDetailActivity);
    }
}
