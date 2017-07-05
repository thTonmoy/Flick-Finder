package com.tht.movies.ui;

import android.content.Intent;
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
import com.tht.movies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
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

        showDetails();
    }

    void showDetails() {
        Intent intentThatStartedActivity = this.getIntent();

        if (intentThatStartedActivity.hasExtra("parcelable_extra")) {
            Movie selectedMovie = intentThatStartedActivity.getParcelableExtra("parcelable_extra");
            adapter.setDetailData(selectedMovie);
            recyclerView.setVisibility(View.VISIBLE);
            collapsingToolbar.setTitle(selectedMovie.title);
            Glide.with(backdropImageView.getContext())
                    .load(selectedMovie.backdrop_path)
                    .into(backdropImageView);

        }
    }

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
}
