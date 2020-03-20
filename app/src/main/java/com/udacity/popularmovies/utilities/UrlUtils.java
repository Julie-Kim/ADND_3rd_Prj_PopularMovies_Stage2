package com.udacity.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.popularmovies.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {

    private static final String TAG = UrlUtils.class.getSimpleName();

    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    private static final String PARAM_POPULAR = "popular";
    private static final String PARAM_TOP_RATED = "top_rated";
    private static final String PARAM_VIDEOS = "videos";
    private static final String PARAM_REVIEWS = "reviews";

    private static final String PARAM_API_KEY = "api_key";
    private static final String API_KEY = BuildConfig.API_KEY;

    public static final int GET_MOVIE = 0;
    public static final int GET_VIDEO = 1;
    public static final int GET_REVIEW = 2;

    public static URL buildUrl(int queryType, String sortByOrId) {
        if (TextUtils.isEmpty(API_KEY)) {
            Log.e(TAG, "buildUri(), no API Key. Please add your own API Key in NetworkUtils.java");
        }

        Uri uri;
        switch (queryType) {
            case GET_VIDEO:
                uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(sortByOrId)
                        .appendPath(PARAM_VIDEOS)
                        .appendQueryParameter(PARAM_API_KEY, API_KEY)
                        .build();
                break;

            case GET_REVIEW:
                uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(sortByOrId)
                        .appendPath(PARAM_REVIEWS)
                        .appendQueryParameter(PARAM_API_KEY, API_KEY)
                        .build();
                break;

            case GET_MOVIE:
            default:
                uri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(sortByOrId)
                        .appendQueryParameter(PARAM_API_KEY, API_KEY)
                        .build();
                break;
        }

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "buildUrl() url = " + url);
        return url;
    }

    public static String getSortByParam(Context context) {
        int value = PreferenceUtils.getSortBySettingValue(context);

        if (value == 1) {
            return PARAM_TOP_RATED;
        }
        return PARAM_POPULAR;
    }
}
