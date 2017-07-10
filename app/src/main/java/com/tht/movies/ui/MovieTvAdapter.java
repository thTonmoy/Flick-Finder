package com.tht.movies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tht.movies.R;
import com.tht.movies.model.Movie;

public class MovieTvAdapter extends RecyclerView.Adapter<MovieTvAdapter.MoviesAdapterViewHolder> {

    private final MoviesAdapterOnClickHandler mClickHandler;
    private Movie[] mMovieData;

    public MovieTvAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {

        final Context context = holder.mMovieImageView.getContext();
        String PosterPath = mMovieData[position].poster_path;
        //URL url = ;
        //String url = TmbdUtils.createImageUrl_P(relativePath);
        Glide.with(context)
                .load(PosterPath)
                .into(holder.mMovieImageView);
        //forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) {
            return 0;
        }
        return mMovieData.length;
    }

    public void setMovieData(Movie[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMovieImageView;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.iv_movie_thumbnail);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieData[adapterPosition];
            mClickHandler.onClick(movie);
        }
    }
}
