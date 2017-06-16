package com.tht.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tht.movies.data.Movie;
import com.tht.movies.utilities.TmbdImageUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView movieOverview;
    private TextView movieRating;
    private TextView movieRelease;
    private ActionBar actionBar;
    private ImageView backdropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        movieOverview = (TextView) findViewById(R.id.tv_movie_overview);
        movieRating = (TextView) findViewById(R.id.tv_movie_rating);
        movieRelease = (TextView) findViewById(R.id.tv_movie_release);
        backdropImageView = (ImageView) findViewById(R.id.iv_movie_backdrop);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        showDetails();
    }

    void showDetails() {
        Intent intentThatStartedActivity = this.getIntent();

        if (intentThatStartedActivity.hasExtra("parcelable_extra")) {
            Movie selectedMovie = intentThatStartedActivity.getParcelableExtra("parcelable_extra");
            actionBar.setTitle(selectedMovie.title);
            movieOverview.setText(selectedMovie.overview);
            String rating = "Rating: " + selectedMovie.vote_average;
            movieRating.setText(rating);
            String release = "Release Date: " + selectedMovie.release_date;
            movieRelease.setText(release);
            Picasso.with(backdropImageView.getContext())
                    .load(TmbdImageUtils.createImageUrl_P(selectedMovie.backdrop_path))
                    .into(backdropImageView);

        }
    }


}
