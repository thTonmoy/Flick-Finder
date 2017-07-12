package com.tht.movies.ui;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tht.movies.R;
import com.tht.movies.data.DbContract;
import com.tht.movies.utilities.TmbdUtils;

public class TvFragment extends Fragment implements
        MovieTvAdapter.MoviesAdapterOnClickHandler {


    public static final int LOADER_ID = 270;
    private static LoaderManager loaderManager;
    private LoaderManager.LoaderCallbacks loaderCallback;

    private boolean preferenceChangedFlag;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv, container, false);

        ProgressBar mProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator_tvshow);
        TextView errorTextView = (TextView) rootView.findViewById(R.id.tv_error_message_display_tvshow);
        errorTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_tv);
        mRecyclerView.setVisibility(View.INVISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        MovieTvAdapter mMoviedapter = new MovieTvAdapter(getActivity(), this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMoviedapter);
        mRecyclerView.setHasFixedSize(true);

        loaderCallback = new MoviesLoaderCallback(TmbdUtils.CONTENT_TYPE_TV, mProgressBar, mMoviedapter, mRecyclerView, getActivity());
        loaderManager = getActivity().getLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, loaderCallback);

        return rootView;
    }

    @Override
    public void onClick(String movie) {
        Context context = getActivity();
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        Uri uriForItemClicked = DbContract.MovieEntry.buildContentUriWithId(movie);
        intentToStartDetailActivity.setData(uriForItemClicked);
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
}
