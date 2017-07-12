package com.tht.movies.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tht.movies.R;

public class MovieTvAdapter extends RecyclerView.Adapter<MovieTvAdapter.MoviesAdapterViewHolder> {

    private final MoviesAdapterOnClickHandler mClickHandler;
    Context mContext;
    private Cursor mCursor;

    public MovieTvAdapter(Context context, MoviesAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_grid_item, parent, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        String movieId = mCursor.getString(MainActivity.INDEX_MOVIE_ID);
        String PosterPath = mCursor.getString(MainActivity.INDEX_POSTER);
        //Log.v("path for poster:  ", PosterPath);
        //Log.v(mContext.getClass().getCanonicalName(), "about to load");
        String title = mCursor.getString(MainActivity.INDEX_TITLE);
        Glide.with(mContext)
                .load(PosterPath)
                .into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public void setMovieData(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
        Log.v("Adapter " + mContext.getClass().getName(), "Updated");
    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(String movie);
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
            mCursor.moveToPosition(adapterPosition);
            String movieId = mCursor.getString(MainActivity.INDEX_MOVIE_ID);
            mClickHandler.onClick(movieId);
        }
    }
}
