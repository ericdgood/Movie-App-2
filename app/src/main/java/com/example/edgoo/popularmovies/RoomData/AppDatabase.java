package com.example.edgoo.popularmovies.RoomData;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.edgoo.popularmovies.Utilities.MoviesInfo;

@Database(entities = {MovieFav.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
