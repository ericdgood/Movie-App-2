package com.example.edgoo.popularmovies.RoomData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_movies")
public class MovieFav {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String Title;
    @ColumnInfo(name = "movieId")
    private String MovieId;
    @ColumnInfo(name = "starStatus")
    private int StarStatus;

    public MovieFav(String Title, String MovieId, int StarStatus) {
        this.Title = Title;
        this.MovieId = MovieId;
        this.StarStatus = StarStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getMovieId() {
        return MovieId;
    }

    public int getStarStatus() {
        return StarStatus;
    }

    public void setStarStatus(int starStatus) {
        StarStatus = starStatus;
    }

}
