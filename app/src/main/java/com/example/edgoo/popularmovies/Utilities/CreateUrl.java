package com.example.edgoo.popularmovies.Utilities;

import android.net.Uri;

import com.example.edgoo.popularmovies.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class CreateUrl {

    private static final String API_KEY_PARAM = "api_key";

    public static URL trailerApiUrl(String movieId, int parm) throws MalformedURLException {
        String rot;

        if (parm == 1){
            rot = "/videos";
        } else {
            rot = "/reviews";
        }

        final String MOVIEDB_URL = "https://api.themoviedb.org/3/movie/" + movieId + rot;
        Uri baseUri = Uri.parse(MOVIEDB_URL);
        Uri.Builder trailerBuild = baseUri.buildUpon();
        trailerBuild.appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY);
        trailerBuild.appendQueryParameter("language", "en-US");
        return new URL(trailerBuild.toString());
    }

    public static URL getApiUrl(String mParm) throws MalformedURLException {

        final String MOVIEDB_URL = "https://api.themoviedb.org/3/movie/" + mParm;
        Uri baseUri = Uri.parse(MOVIEDB_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter(API_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY);
        uriBuilder.appendQueryParameter("language", "en-US");
        uriBuilder.appendQueryParameter("page", "1");

        return new URL(uriBuilder.toString());
    }
}
