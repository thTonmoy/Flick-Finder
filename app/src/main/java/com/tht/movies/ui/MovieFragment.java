package com.tht.movies.ui;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tht.movies.R;
import com.tht.movies.model.Movie;
import com.tht.movies.utilities.TmbdUtils;

public class MovieFragment extends Fragment implements
        MovieTvAdapter.MoviesAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int LOADER_ID = 116;
    private static LoaderManager loaderManager;
    MoviesLoaderCallback loaderCallback;

    private boolean preferenceChangedFlag;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        ProgressBar mProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);
        TextView errorTextView = (TextView) rootView.findViewById(R.id.tv_error_message_display);
        errorTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_movies);
        mRecyclerView.setVisibility(View.INVISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        MovieTvAdapter mMoviedapter = new MovieTvAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMoviedapter);
        mRecyclerView.setHasFixedSize(true);
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preference.registerOnSharedPreferenceChangeListener(this);

        loaderCallback = new MoviesLoaderCallback(TmbdUtils.CONTENT_TYPE_MOVIE, mProgressBar, errorTextView, preference, mMoviedapter, mRecyclerView, getActivity());
        loaderManager = getActivity().getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, loaderCallback);

        return rootView;
    }

    @Override
    public void onClick(Movie movie) {
        Context context = getActivity();
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("parcelable_extra", movie);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (preferenceChangedFlag) {
            loaderManager.restartLoader(LOADER_ID, null, loaderCallback);
            preferenceChangedFlag = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        preferenceChangedFlag = true;
    }
}