package com.udacity.popularmovies.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class MovieJsonUtils {

    private static final String TAG = MovieJsonUtils.class.getSimpleName();

    private static final String JSON_TAG_RESULTS = "results";

    private static final String JSON_TAG_TITLE = "title";
    private static final String JSON_TAG_ORIGINAL_TITLE = "original_title";
    private static final String JSON_TAG_POSTER_PATH = "poster_path";
    private static final String JSON_TAG_OVERVIEW = "overview";
    private static final String JSON_TAG_VOTE_AVERAGE = "vote_average";
    private static final String JSON_TAG_RELEASE_DATE = "release_date";

    public static ArrayList<Movie> parseMovieJson(String json) {
        ArrayList<Movie> movieList = new ArrayList<>();

        if (json == null || TextUtils.isEmpty(json)) {
            Log.e(TAG, "parseMovieJson() json string is empty.");
            return movieList;
        }

        try {
            JSONObject movieObject = new JSONObject(json);

            JSONArray resultsArray = movieObject.optJSONArray(JSON_TAG_RESULTS);
            if (resultsArray != null) {
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultObject = resultsArray.optJSONObject(i);

                    String title = resultObject.optString(JSON_TAG_TITLE);
                    String originalTitle = resultObject.optString(JSON_TAG_ORIGINAL_TITLE);
                    String posterPath = resultObject.optString(JSON_TAG_POSTER_PATH);
                    String overview = resultObject.optString(JSON_TAG_OVERVIEW);
                    float voteAverage = (float) resultObject.optDouble(JSON_TAG_VOTE_AVERAGE);
                    String releaseDate = resultObject.optString(JSON_TAG_RELEASE_DATE);

                    Movie movie = new Movie(title, originalTitle, posterPath, overview, voteAverage, releaseDate);
                    Log.d(TAG, "[" + i + "] " + movie.toString());
                    movieList.add(movie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }
}
