package com.udacity.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieVideo implements Parcelable {

    private String mKey;
    private String mName;
    private String mSite;
    private String mType;

    public MovieVideo(String key, String name, String site, String type) {
        mKey = key;
        mName = name;
        mSite = site;
        mType = type;
    }

    private MovieVideo(Parcel in) {
        mKey = in.readString();
        mName = in.readString();
        mSite = in.readString();
        mType = in.readString();
    }

    public static final Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {
        @Override
        public MovieVideo createFromParcel(Parcel in) {
            return new MovieVideo(in);
        }

        @Override
        public MovieVideo[] newArray(int size) {
            return new MovieVideo[size];
        }
    };

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        mSite = site;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "Key: " + mKey +
                "\nName: " + mName +
                "\nSite: " + mSite +
                "\nType: " + mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeString(mName);
        dest.writeString(mSite);
        dest.writeString(mType);
    }
}
