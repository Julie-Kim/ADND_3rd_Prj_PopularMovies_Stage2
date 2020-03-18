package com.udacity.popularmovies.utilities;

public final class MovieDataUtils {

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    public static String getMoviePosterFullPath(String moviePath) {
        return IMAGE_BASE_URL + IMAGE_SIZE + moviePath;
    }
}
