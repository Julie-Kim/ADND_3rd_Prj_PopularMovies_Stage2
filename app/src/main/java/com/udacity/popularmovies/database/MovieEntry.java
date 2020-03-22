package com.udacity.popularmovies.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int mId;

    @ColumnInfo(name = "movie_id")
    private String mMovieId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "original_title")
    private String mOriginalTitle;

    @ColumnInfo(name = "poster_path")
    private String mPosterPath;

    @ColumnInfo(name = "poster_image", typeAffinity = ColumnInfo.BLOB)
    private byte[] mPosterImage;

    @ColumnInfo(name = "overview")
    private String mOverview;   //A plot synopsis

    @ColumnInfo(name = "vote_average")
    private float mVoteAverage;    //user rating

    @ColumnInfo(name = "release_date")
    private String mReleaseDate;

    public MovieEntry(int id, String movieId, String title, String originalTitle, String posterPath, byte[] posterImage, String overview, float voteAverage, String releaseDate) {
        mId = id;
        mMovieId = movieId;
        mTitle = title;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mPosterImage = posterImage;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    @Ignore
    public MovieEntry(String movieId, String title, String originalTitle, String posterPath, String overview, float voteAverage, String releaseDate) {
        mMovieId = movieId;
        mTitle = title;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    @Ignore
    private MovieEntry(Parcel parcel) {
        mId = parcel.readInt();
        mMovieId = parcel.readString();
        mTitle = parcel.readString();
        mOriginalTitle = parcel.readString();
        mPosterPath = parcel.readString();
        mOverview = parcel.readString();
        mVoteAverage = parcel.readFloat();
        mReleaseDate = parcel.readString();
    }

    public static final Creator<MovieEntry> CREATOR = new Creator<MovieEntry>() {
        @Override
        public MovieEntry createFromParcel(Parcel in) {
            return new MovieEntry(in);
        }

        @Override
        public MovieEntry[] newArray(int size) {
            return new MovieEntry[size];
        }
    };

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

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
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

    @NonNull
    @Override
    public String toString() {
        return "MovieId: " + mMovieId +
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
        dest.writeInt(mId);
        dest.writeString(mMovieId);
        dest.writeString(mTitle);
        dest.writeString(mOriginalTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeFloat(mVoteAverage);
        dest.writeString(mReleaseDate);
    }
}
