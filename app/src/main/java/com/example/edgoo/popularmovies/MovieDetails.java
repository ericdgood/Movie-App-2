package com.example.edgoo.popularmovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgoo.popularmovies.RoomData.AppDatabase;
import com.example.edgoo.popularmovies.RoomData.MovieFav;
import com.example.edgoo.popularmovies.Utilities.CreateUrl;
import com.example.edgoo.popularmovies.Utilities.FetchJson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    private static String[] mTrailerName;
    private static String[] mTrailerKey;
    private static String[] mReviewAuthor;
    private static String[] mReviewContent;
    private LinearLayout mTrailerList;
    private LinearLayout mReviewList;
    private ImageView mStar;
    private int StarStatus = 0;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        getIntentAndSet();

        mTrailerList = findViewById(R.id.trailer_layout);
        mReviewList = findViewById(R.id.review_list);
        String movieId = (getIntent().getStringExtra("movieId"));
        String movieTitle = (getIntent().getStringExtra("movie_title"));
        new TrailerAsync(movieId).execute();
        new ReviewAsync(movieId).execute();
        FavoriteAdd(movieId, movieTitle);

    }

    //    TRAILER INFO IS FETCHED AND POPULATED
    public class TrailerAsync extends AsyncTask<String, Void, String> {

        private final String mMovieId;

        TrailerAsync(String movieId) {
            mMovieId = movieId;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL trilerUrl = CreateUrl.trailerApiUrl(mMovieId, 1);
                return FetchJson.getResponseFromHttpUrl(trilerUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String trailerJson) {
            try {
                MovieDetails.parseMovieTrailers(trailerJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            populateTrailerInfo();
        }
    }

    public static void parseMovieTrailers(String trailerJson) throws JSONException {

        JSONObject trailerJsonObject = new JSONObject(trailerJson);
//        GETS ARRAY OF MOVIE FROM RESULTS FOR TRAILERS
        JSONArray trailerArray = trailerJsonObject.getJSONArray("results");
        mTrailerKey = new String[trailerArray.length()];
        mTrailerName = new String[trailerArray.length()];
        for (int i = 0; i < trailerArray.length(); i++) {
            JSONObject currentTrailer = trailerArray.getJSONObject(i);
            mTrailerKey[i] = (currentTrailer.getString("key"));
            mTrailerName[i] = (currentTrailer.getString("name"));
        }
    }

    public void populateTrailerInfo() {

        if (mTrailerKey.length == 0) {
            LinearLayout trailerItem = new LinearLayout(this);
            TextView trailername = new TextView(this);
            trailername.setText(R.string.no_trailers);
            trailername.setPadding(0, 30, 0, 30);
            trailername.setTextSize(21);
            trailerItem.addView(trailername);
            trailerItem.setGravity(Gravity.CENTER);
            mTrailerList.addView(trailerItem);
        } else {
            for (int i = 0; i < mTrailerKey.length; i++) {
                LinearLayout trailerItem = new LinearLayout(this);
                TextView trailername = new TextView(this);
                ImageView newPlayBtn = new ImageView(this);
                newPlayBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                trailername.setText(mTrailerName[i]);
                trailername.setPadding(0, 30, 0, 30);
                trailername.setTextSize(16);
                trailerItem.addView(newPlayBtn);
                trailerItem.addView(trailername);
                trailerItem.setGravity(Gravity.CENTER);
                String trailerKey = mTrailerKey[i];
                trailerItem.setOnClickListener(v -> watchYoutubeVideo(trailerKey));
                mTrailerList.addView(trailerItem);
            }
        }
    }


    public void watchYoutubeVideo(String movieKey) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieKey));
        startActivity(intent);
    }

    private void getIntentAndSet() {
        TextView title = findViewById(R.id.movie_detail_name);
        title.setText(getIntent().getStringExtra("movie_title"));
        TextView overviewView = findViewById(R.id.overview);
        overviewView.setText(getIntent().getStringExtra("overview"));
        TextView releaseView = findViewById(R.id.release);
        releaseView.setText(getIntent().getStringExtra("release"));
        TextView voteView = findViewById(R.id.vote);
        voteView.setText(getIntent().getStringExtra("vote"));
        ImageView posterView = findViewById(R.id.poster_detail_view);
        Picasso.with(this)
                .load(getIntent().getStringExtra("poster"))
                .into(posterView);
    }

//    REVIEWS ARE FETCHED AND POPULATED

    public class ReviewAsync extends AsyncTask<String, Void, String> {

        private final String mMovieId;

        ReviewAsync(String movieId) {
            mMovieId = movieId;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL reviewUrl = CreateUrl.trailerApiUrl(mMovieId, 0);
                return FetchJson.getResponseFromHttpUrl(reviewUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String reviewJson) {
            try {
                parseReview(reviewJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            populateReviews();
        }
    }

    public static void parseReview(String reviewJson) throws JSONException {

        JSONObject reviewJsonObject = new JSONObject(reviewJson);
//        GETS ARRAY OF MOVIE FROM RESULTS FOR TRAILERS
        JSONArray reviewArray = reviewJsonObject.getJSONArray("results");
        mReviewAuthor = new String[reviewArray.length()];
        mReviewContent = new String[reviewArray.length()];
        for (int i = 0; i < reviewArray.length(); i++) {
            JSONObject currentReview = reviewArray.getJSONObject(i);
            mReviewAuthor[i] = (currentReview.getString("author"));
            mReviewContent[i] = (currentReview.getString("content"));
        }
    }

    private void populateReviews() {

        if (mReviewAuthor.length == 0) {
            TextView noReviews = new TextView(this);
            noReviews.setText(R.string.no_reviews);
            noReviews.setTextSize(21);
            noReviews.setGravity(Gravity.CENTER);
            mReviewList.addView(noReviews);
        } else {

            for (int i = 0; i < mReviewAuthor.length; i++) {
                LinearLayout reviewlayout = new LinearLayout(this);
                reviewlayout.setOrientation(LinearLayout.VERTICAL);
                reviewlayout.setPadding(20, 0, 20, 0);


                View dividingLine = new View(this);
                dividingLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                dividingLine.setBackgroundColor(0xFF000000);
                reviewlayout.addView(dividingLine);

                TextView authorname = new TextView(this);
                authorname.setText(mReviewAuthor[i]);
                authorname.setTextSize(18);
                authorname.setPadding(0, 0, 0, 30);
                reviewlayout.addView(authorname);

                TextView reviewContent = new TextView(this);
                reviewContent.setPadding(10, 0, 10, 30);
                reviewContent.setText(mReviewContent[i]);

                reviewlayout.addView(reviewContent);

                mReviewList.addView(reviewlayout);
            }
        }
    }

    private void FavoriteAdd(String movieId, String movieTitle) {
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "favorite_movies")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        int starStat = db.movieDao().getStarStatus(movieId);

        mStar = findViewById(R.id.star);
        mStar.setColorFilter(0xFFFBD602);

        if (starStat == 1){
            mStar.setImageResource(R.drawable.ic_star);
            StarStatus = 1;
        }

        mStar.setOnClickListener(v -> {
            if (StarStatus == 1) {
//            REMOVE FAVORITE
                StarStatus = 0;
                mStar.setImageResource(R.drawable.ic_star_border);
                db.movieDao().deleteByUserId(movieId);
                Toast.makeText(this, "Movie removed from your favorites list!",
                        Toast.LENGTH_SHORT).show();

            } else if (StarStatus == 0) {
//            ADD TO FAVORITE
                StarStatus = 1;
                mStar.setImageResource(R.drawable.ic_star);
                final MovieFav saveMovie = new MovieFav(movieTitle, movieId, StarStatus);
                db.movieDao().insertAll(saveMovie);
                Toast.makeText(this, "Movie added to your favorites list!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        }
    }

