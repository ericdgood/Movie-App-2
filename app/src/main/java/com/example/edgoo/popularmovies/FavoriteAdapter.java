package com.example.edgoo.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.edgoo.popularmovies.RoomData.MovieFav;

import java.util.List;

class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<MovieFav> favMovies;

    FavoriteAdapter(List<MovieFav> favMovieList) {
        this.favMovies = favMovieList;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_movie_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.movieTitle.setText(favMovies.get(position).getTitle());
        holder.movieId.setText(favMovies.get(position).getMovieId());

    }

    @Override
    public int getItemCount() {
        return favMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView movieId;

        ViewHolder(View view) {
            super(view);
            movieTitle = view.findViewById(R.id.fav_movie_title);
            movieId = view.findViewById(R.id.fav_movie_id);
        }
    }
}
