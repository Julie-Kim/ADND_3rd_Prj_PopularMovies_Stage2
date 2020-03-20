package com.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Movie implements Parcelable {

    private String mId;
    private String mTitle;
    private String mOriginalTitle;
    private String mPosterPath;
    private String mOverview;   //A plot synopsis
    private float mVoteAverage;    //user rating
    private String mReleaseDate;

    public Movie(String id, String title, String originalTitle, String posterPath, String overview, float voteAverage, String releaseDate) {
        mId = id;
        mTitle = title;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    private Movie(Parcel parcel) {
        mId = parcel.readString();
        mTitle = parcel.readString();
        mOriginalTitle = parcel.readString();
        mPosterPath = parcel.readString();
        mOverview = parcel.readString();
        mVoteAverage = parcel.readFloat();
        mReleaseDate = parcel.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
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

    @NonNull
    @Override
    public String toString() {
        return "Id: " + mId +
                "\nTitle: " + mTitle +
                "\nOriginalTitle: " + mOriginalTitle +
                "\nPosterPath: " + mPosterPath +
                "\nOverview: " + mOverview +
                "\nVoteAverage: " + mVoteAverage +
                "\nReleaseDate: " + mReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mOriginalTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeFloat(mVoteAverage);
        dest.writeString(mReleaseDate);
    }
}
