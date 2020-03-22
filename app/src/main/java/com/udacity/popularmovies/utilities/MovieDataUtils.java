package com.udacity.popularmovies.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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

    public static byte[] getBlobImage(String posterPath) {
        try {
            URL imageUrl = new URL(getMoviePosterFullPath(posterPath));
            URLConnection urlConnection = imageUrl.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[500];
            int current;

            while ((current = bufferedInputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, current);
            }

            return buffer.toByteArray();
        } catch (Exception e) {
            Log.e(TAG, "getBlobImage() " + e.toString());
        }
        return null;
    }

    public static Bitmap getBitmapFromBlob(byte[] imageByteArray) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
        return BitmapFactory.decodeStream(imageStream);
    }
}
