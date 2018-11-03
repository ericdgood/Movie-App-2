package com.example.edgoo.popularmovies.Utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ParseMoviedb {

    public static MoviesInfo[] parseMovieJson(String jsonResponse) throws JSONException, IOException {

//          CREATES JSON OBJECT WITH JSON STRING
        JSONObject mainJsonObject = new JSONObject(jsonResponse);
//        GETS ARRAY OF MOVIE FROM RESULTS
        JSONArray movieTitleArray = mainJsonObject.getJSONArray("results");
//        ARRAY OF PARSED MOVIE INFO
        MoviesInfo[] movie = new MoviesInfo[movieTitleArray.length()];

//                 LOOPS THROUGH EACH MOVE ARRAY RESULT
        for (int i = 0; i < movieTitleArray.length(); i++) {

            movie[i] = new MoviesInfo();
//                CREATES A CURRENT MOVIE OBJECT
            JSONObject currentMovie = movieTitleArray.getJSONObject(i);
//                USED TO GET POSTER OF MOVIE
            String poster_path = currentMovie.getString("poster_path");
            String poster = "http://image.tmdb.org/t/p/w185/" + poster_path;
            movie[i].setPoster(poster);

            String detail_poster_path = currentMovie.getString("poster_path");
            String detail_poster = "http://image.tmdb.org/t/p/w500/" + detail_poster_path;
            movie[i].setDetailMoviePoster(detail_poster);

//            GETS TITLE OF MOVIE
            movie[i].setTitle(currentMovie.getString("title"));

//            GETS OVERVIEW OF MOVIE
            movie[i].setOverview(currentMovie.getString("overview"));

//            GET RELEASE DATE (2018-06-06)
            String release_date = currentMovie.getString("release_date");
            movie[i].setReleaseDate(release_date.substring(0, Math.min(release_date.length(), 4)));

//            GET VOTE AVERAGE
            movie[i].setVoteAverage(currentMovie.getString("vote_average"));

//            GETS ID OF MOVIE FOR TRAILER API
            movie[i].setMovieId(currentMovie.getString("id"));
        }
        return movie;
    }


    public static String parseReview(String reviewJson) throws JSONException {

        JSONObject trailerJsonObject = new JSONObject(reviewJson);
//        GETS ARRAY OF MOVIE FROM RESULTS FOR TRAILERS
        JSONArray trailerArray = trailerJsonObject.getJSONArray("results");

        String reviewAuthor = null;
        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject currentReview = trailerArray.getJSONObject(i);
            reviewAuthor = (currentReview.getString("author"));
        }
        return reviewAuthor;
    }
}