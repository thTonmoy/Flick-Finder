package com.tht.movies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tht.movies.R;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MovieFragment())
                .commit();
    }
}
