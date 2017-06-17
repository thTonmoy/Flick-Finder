package com.tht.movies.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tht.movies.R;
import com.tht.movies.model.Movie;


public class DetailRecyclingAdapter extends RecyclerView.Adapter<DetailRecyclingAdapter.DetailAdapterViewHolder> {

    private int itemInDetails = 3;
    private String[] detailData;

    @Override
    public DetailRecyclingAdapter.DetailAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.detail_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new DetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailRecyclingAdapter.DetailAdapterViewHolder holder, int position) {
        holder.detailItemTextView.setText(detailData[position]);
    }

    @Override
    public int getItemCount() {
        if (detailData != null) {
            return itemInDetails;
        } else {
            return 0;
        }

    }

    public void setDetailData(Movie data) {
        detailData = new String[itemInDetails];
        detailData[0] = "Summary:\n\n" + data.overview;
        detailData[1] = "Rating: \n\n" + data.vote_average + "/10";
        detailData[2] = "Release Date: \n" + data.release_date;
        notifyDataSetChanged();
    }

    public class DetailAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView detailItemTextView;

        public DetailAdapterViewHolder(View itemView) {
            super(itemView);
            detailItemTextView = (TextView) itemView.findViewById(R.id.tv_movie_detail);

        }
    }
}
