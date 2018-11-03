package com.example.edgoo.popularmovies.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

public class MoviesInfo implements Parcelable {

    private String title;
    private String movieId;
    private String poster;
    private String overview;
    private String releaseDate;
    private String voteAverage;
    private String detailMoviePoster;

    private MoviesInfo(Parcel in) {
        title = in.readString();
        poster = in.readString();
        movieId = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readString();
        detailMoviePoster = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(movieId);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(detailMoviePoster);
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    MoviesInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MoviesInfo> CREATOR = new Creator<MoviesInfo>() {
        @Override
        public MoviesInfo createFromParcel(Parcel in) {
            return new MoviesInfo(in);
        }

        @Override
        public MoviesInfo[] newArray(int size) {
            return new MoviesInfo[size];
        }
    };


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getDetailMoviePoster() {
        return detailMoviePoster;
    }

    public void setDetailMoviePoster(String detailMoviePoster) {
        this.detailMoviePoster = detailMoviePoster;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }
}
