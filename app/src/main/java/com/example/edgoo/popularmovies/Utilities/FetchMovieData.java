package com.example.edgoo.popularmovies.Utilities;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edgoo.popularmovies.MovieAdapter;
import com.example.edgoo.popularmovies.MovieDetails;
import com.example.edgoo.popularmovies.R;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class FetchMovieData extends AsyncTask<String, Void, MoviesInfo[]> {

    private final MovieAdapter mMovieAdapter;
    private final String mParm;

    public FetchMovieData(MovieAdapter movieAdapter, String parm) {
        mMovieAdapter = movieAdapter;
        mParm = parm;
    }

    @Override
    protected MoviesInfo[] doInBackground(String... strings) {

        URL movieUrl = null;
        try {
            movieUrl = CreateUrl.getApiUrl(mParm);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            assert movieUrl != null;
//                BUILDS MOVIEDB URL INTO STRING URL
            String jsonresponse = FetchJson.getResponseFromHttpUrl(movieUrl);
//                PARES MOVIEDB URL
            return ParseMoviedb.parseMovieJson(jsonresponse);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(MoviesInfo[] movieData) {
        mMovieAdapter.setMovieData(movieData);
    }
}
