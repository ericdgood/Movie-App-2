package com.example.edgoo.popularmovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.edgoo.popularmovies.RoomData.AppDatabase;
import com.example.edgoo.popularmovies.RoomData.MovieFav;
import com.example.edgoo.popularmovies.Utilities.FetchMovieData;
import com.example.edgoo.popularmovies.Utilities.MoviesInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter mMovieAdapter;
    private String mParm = "popular";
    MoviesInfo[] mMovies;
    private List<MovieFav> favMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView mGridView = findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(moviePosterClickListener);
        mMovieAdapter = new MovieAdapter(this, mMovies);
        mGridView.setAdapter(mMovieAdapter);
        setMyTitle(0);
        loadMovieData();
    }

    private final GridView.OnItemClickListener moviePosterClickListener = (parent, view, position, id) -> {
        MoviesInfo movie = (MoviesInfo) parent.getItemAtPosition(position);

        Intent intent = new Intent(getApplicationContext(), MovieDetails.class);
        intent.putExtra("movie_title", movie.getTitle());
        intent.putExtra("overview", movie.getOverview());
        intent.putExtra("release", movie.getReleaseDate());
        intent.putExtra("vote", movie.getVoteAverage());
        intent.putExtra("poster", movie.getDetailMoviePoster());
        intent.putExtra("movieId", movie.getMovieId());

        startActivity(intent);
    };

    private void loadMovieData() {
        new FetchMovieData(mMovieAdapter, mParm).execute();
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void setMyTitle(Integer setTitleTo){
        if(setTitleTo == 1){
            setTitle(getString(R.string.title_ratings));
        } else if(setTitleTo == 0) {
            setTitle(getString(R.string.title_popular));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_top_rate:
                mParm = getString(R.string.ratings_parm);
                setMyTitle(1);
                loadMovieData();
                return true;
            case R.id.sort_popular:
                mParm = getString(R.string.popular_parm);
                setMyTitle(0);
                loadMovieData();
                return true;
            case R.id.favorite_movies:
                startActivity(new Intent(MainActivity.this, FavoriteMovieList.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

