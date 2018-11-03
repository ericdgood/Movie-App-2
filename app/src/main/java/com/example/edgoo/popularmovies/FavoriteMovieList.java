package com.example.edgoo.popularmovies;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.edgoo.popularmovies.R;
import com.example.edgoo.popularmovies.RoomData.AppDatabase;
import com.example.edgoo.popularmovies.RoomData.MovieFav;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieList extends AppCompatActivity {


    private List<MovieFav> favMovieList = new ArrayList<>();
    private RecyclerView RecyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.favorite_movies);
        setContentView(R.layout.favortie_movies);
        RecyclerView = findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.setLayoutManager(layoutManager);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "favorite_movies")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        favMovieList = db.movieDao().getAllMovies();
        adapter = new FavoriteAdapter(favMovieList);
        RecyclerView.setAdapter(adapter);
    }
}
