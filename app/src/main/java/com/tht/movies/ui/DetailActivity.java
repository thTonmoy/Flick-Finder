package com.tht.movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.tht.movies.utilities.TmbdImageUtils;

public class DetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    private ImageView backdropImageView;
    private RecyclerView recyclerView;
    private DetailRecyclingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        backdropImageView = (ImageView) findViewById(R.id.iv_movie_backdrop);
        recyclerView = (RecyclerView) findViewById(R.id.rv_detail);
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
                    .load(TmbdImageUtils.createImageUrl_P(selectedMovie.backdrop_path))
                    .into(backdropImageView);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_to_watchlist, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add_to_watchlist) {
            if (!item.isChecked()) {
                item.setIcon(R.drawable.ic_favorite_black_24dp);
                Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
            } else {
                item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                Toast.makeText(this, "Removed from favourites", Toast.LENGTH_SHORT).show();
                item.setChecked(false);
            }
        }

        return super.onOptionsItemSelected(item);

    }
}
