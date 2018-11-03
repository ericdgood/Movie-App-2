package com.example.edgoo.popularmovies.RoomData;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    List<MovieFav> getAllMovies();

    @Insert
    void insertAll(MovieFav... movies);

    @Query("DELETE FROM favorite_movies WHERE movieId = :movieId")
    void deleteByUserId(String movieId);

    @Query("SELECT starStatus FROM favorite_movies WHERE movieId = :movieId")
    int getStarStatus(String movieId);
}
