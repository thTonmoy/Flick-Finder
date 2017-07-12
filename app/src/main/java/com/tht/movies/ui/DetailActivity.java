package com.tht.movies.ui;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tht.movies.R;
import com.tht.movies.data.DbContract;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    public final static int DETAIL_LOADER_ID = 453;
    public static final String[] MOVIE_DETAIL_PROJECTION = {
            DbContract.MovieEntry.COLUMN_TITLE,
            DbContract.MovieEntry.COLUMN_BACKDROP,
            DbContract.MovieEntry.COLUMN_OVERVIEW,
            DbContract.MovieEntry.COLUMN_RELEASE_DATE,
            DbContract.MovieEntry.COLUMN_VOTE_AVG
    };
    public static final int INDEX_TITLE = 0;
    public static final int INDEX_BACKDROP = 1;
    public static final int INDEX_OVERVIEW = 2;
    public static final int INDEX_RELEASE_DATE = 3;
    public static final int INDEX_VOTE_AVG = 4;
    CollapsingToolbarLayout collapsingToolbar;
    Uri mUri;
    private ImageView backdropImageView;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DetailRecyclingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        backdropImageView = (ImageView) findViewById(R.id.iv_movie_backdrop);
        recyclerView = (RecyclerView) findViewById(R.id.rv_detail);
        fab = (FloatingActionButton) findViewById(R.id.detail_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_favorite_black_24dp));
                Toast.makeText(v.getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new DetailRecyclingAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mUri = getIntent().getData();
        if (mUri == null) throw new NullPointerException("URI for DetailActivity cannot be null");

        //showDetails();
        getLoaderManager().initLoader(DETAIL_LOADER_ID, null, this);
    }

    /*
        private Intent createShareForecastIntent() {
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(mForecastSummary + FORECAST_SHARE_HASHTAG)
                    .getIntent();
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            return shareIntent;
        }
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_activity, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add_to_watchlist) {

        } else if (id == R.id.share_movie_info) {
            Toast.makeText(this, "To be implemented", Toast.LENGTH_SHORT).show();
            //ShareCompat.IntentBuilder.from(this).setType("Text/Plain").setText("Movie").setChooserTitle("title").getIntent();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case DETAIL_LOADER_ID:

                return new CursorLoader(this, mUri, MOVIE_DETAIL_PROJECTION, null, null, null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            adapter.setDetailData(data);
            recyclerView.setVisibility(View.VISIBLE);
            String title = data.getString(INDEX_TITLE);
            collapsingToolbar.setTitle(title);
            String backdrop = data.getString(INDEX_BACKDROP);
            Glide.with(backdropImageView.getContext())
                    .load(backdrop)
                    .into(backdropImageView);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
