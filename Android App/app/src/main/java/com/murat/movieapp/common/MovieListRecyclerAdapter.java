package com.murat.movieapp.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.murat.movieapp.model.MovieResponse;
import com.murat.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieResponse> mList;

    public MovieListRecyclerAdapter(List<MovieResponse> list) {
        this.mList = list;
    }

    public MovieListRecyclerAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieListRecyclerAdapter.ViewHolderTypes(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MovieResponse model = mList.get(position);

        ViewHolderTypes viewHolder = (ViewHolderTypes) holder;
        viewHolder.tvTitle.setText(model.getTitle());
        viewHolder.tvDate.setText(model.getReleased());
        viewHolder.tvDirector.setText(model.getDirector());
        viewHolder.tvRate.setText(model.getImdbRating());
        Picasso.get().load(model.getPoster()).into(viewHolder.posterImage);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolderTypes extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDate, tvDirector, tvRate;
        ImageView posterImage;

        private ViewHolderTypes(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDirector = itemView.findViewById(R.id.tvDirector);
            tvRate = itemView.findViewById(R.id.tvRate);
            posterImage = itemView.findViewById(R.id.poster);

        }
    }


}
