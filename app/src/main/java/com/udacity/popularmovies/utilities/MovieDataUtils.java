package com.udacity.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

public final class MovieDataUtils {

    private static final String TAG = MovieDataUtils.class.getSimpleName();

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    public static String getMoviePosterFullPath(String moviePath) {
        return IMAGE_BASE_URL + IMAGE_SIZE + moviePath;
    }

    public static Uri getYoutubeUri(String key) {
        Uri youtubeUri = Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                .appendPath(key)
                .build();

        Log.d(TAG, "getYoutubeUri() youtubeUri = " + youtubeUri);
        return youtubeUri;
    }
}
