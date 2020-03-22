package com.udacity.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.udacity.popularmovies.model.Movie;

@Entity(tableName = "movie")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int mId;

    @ColumnInfo(name = "movie_id")
    private String mMovieId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "original_title")
    private String mOriginalTitle;

    @ColumnInfo(name = "poster_image", typeAffinity = ColumnInfo.BLOB)
    private byte[] mPosterImage;

    @ColumnInfo(name = "overview")
    private String mOverview;

    @ColumnInfo(name = "vote_average")
    private float mVoteAverage;

    @ColumnInfo(name = "release_date")
    private String mReleaseDate;

    @Ignore
    public MovieEntry(Movie movie) {
        mMovieId = movie.getId();
        mTitle = movie.getTitle();
        mOriginalTitle = movie.getOriginalTitle();
        //mPosterImage = mPosterImage;    //TODO
        mOverview = movie.getOverview();
        mVoteAverage = movie.getVoteAverage();
        mReleaseDate = movie.getReleaseDate();
    }

    public MovieEntry(int id, String movieId, String title, String originalTitle, byte[] posterImage, String overview, float voteAverage, String releaseDate) {
        mId = id;
        mMovieId = movieId;
        mTitle = title;
        mOriginalTitle = originalTitle;
        mPosterImage = posterImage;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getMovieId() {
        return mMovieId;
    }

    public void setMovieId(String movieId) {
        mMovieId = movieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public byte[] getPosterImage() {
        return mPosterImage;
    }

    public void setPosterImage(byte[] posterImage) {
        mPosterImage = posterImage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }
}
