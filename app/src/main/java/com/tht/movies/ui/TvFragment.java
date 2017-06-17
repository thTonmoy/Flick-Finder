package com.tht.movies.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tht.movies.R;
import com.tht.movies.model.Movie;
import com.tht.movies.utilities.JsonUtils;
import com.tht.movies.utilities.NetworkUtils;

import java.io.IOException;

public class TvFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Movie[]>, MoviesAdapter.MoviesAdapterOnClickHandler {


    private static final int LOADER_ID = 270;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView errorTextView;
    private MoviesAdapter mMoviedapter;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator_tvshow);
        errorTextView = (TextView) rootView.findViewById(R.id.tv_error_message_display_tvshow);
        errorTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_tv);
        mRecyclerView.setVisibility(View.INVISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mMoviedapter = new MoviesAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMoviedapter);
        mRecyclerView.setHasFixedSize(true);
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Movie[]>(getContext()) {
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
                    String s = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getRequestUrlTv());
                    mMovieDataArray = JsonUtils.getTvInfoFromJson(s);
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
        Context context = getContext();
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("parcelable_extra", movie);
        startActivity(intentToStartDetailActivity);
    }
}
