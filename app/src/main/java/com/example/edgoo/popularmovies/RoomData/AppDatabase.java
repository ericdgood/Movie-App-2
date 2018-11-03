package com.example.edgoo.popularmovies.RoomData;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MovieFav.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
