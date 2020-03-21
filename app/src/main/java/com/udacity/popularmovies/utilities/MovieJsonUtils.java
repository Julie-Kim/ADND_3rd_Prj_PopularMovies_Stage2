package com.udacity.popularmovies.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.model.MovieReview;
import com.udacity.popularmovies.model.MovieVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class MovieJsonUtils {

    private static final String TAG = MovieJsonUtils.class.getSimpleName();

    private static final String JSON_TAG_RESULTS = "results";

    /* Movie data */
    private static final String JSON_TAG_ID = "id";
    private static final String JSON_TAG_TITLE = "title";
    private static final String JSON_TAG_ORIGINAL_TITLE = "original_title";
    private static final String JSON_TAG_POSTER_PATH = "poster_path";
    private static final String JSON_TAG_OVERVIEW = "overview";
    private static final String JSON_TAG_VOTE_AVERAGE = "vote_average";
    private static final String JSON_TAG_RELEASE_DATE = "release_date";

    /* Video data */
    private static final String JSON_TAG_KEY = "key";
    private static final String JSON_TAG_NAME = "name";
    private static final String JSON_TAG_SITE = "site";
    private static final String JSON_TAG_TYPE = "type";

    /* Review data */
    private static final String JSON_TAG_AUTHOR = "author";
    private static final String JSON_TAG_CONTENT = "content";
    private static final String JSON_TAG_URL = "url";

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

                    String id = resultObject.optString(JSON_TAG_ID);
                    String title = resultObject.optString(JSON_TAG_TITLE);
                    String originalTitle = resultObject.optString(JSON_TAG_ORIGINAL_TITLE);
                    String posterPath = resultObject.optString(JSON_TAG_POSTER_PATH);
                    String overview = resultObject.optString(JSON_TAG_OVERVIEW);
                    float voteAverage = (float) resultObject.optDouble(JSON_TAG_VOTE_AVERAGE);
                    String releaseDate = resultObject.optString(JSON_TAG_RELEASE_DATE);

                    Movie movie = new Movie(id, title, originalTitle, posterPath, overview, voteAverage, releaseDate);
                    Log.d(TAG, "parseMovieJson, [" + i + "] " + movie.toString());
                    movieList.add(movie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public static ArrayList<MovieVideo> parseVideoJson(String json) {
        ArrayList<MovieVideo> videoList = new ArrayList<>();

        if (json == null || TextUtils.isEmpty(json)) {
            Log.e(TAG, "parseVideoJson() json string is empty.");
            return videoList;
        }

        try {
            JSONObject videoObject = new JSONObject(json);

            JSONArray resultsArray = videoObject.optJSONArray(JSON_TAG_RESULTS);
            if (resultsArray != null) {
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultObject = resultsArray.optJSONObject(i);

                    String key = resultObject.optString(JSON_TAG_KEY);
                    String name = resultObject.optString(JSON_TAG_NAME);
                    String site = resultObject.optString(JSON_TAG_SITE);
                    String type = resultObject.optString(JSON_TAG_TYPE);

                    MovieVideo video = new MovieVideo(key, name, site, type);
                    Log.d(TAG, "parseVideoJson, [" + i + "] " + video.toString());
                    videoList.add(video);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videoList;
    }

    public static ArrayList<MovieReview> parseReviewJson(String json) {
        ArrayList<MovieReview> reviewList = new ArrayList<>();

        if (json == null || TextUtils.isEmpty(json)) {
            Log.e(TAG, "parseReviewJson() json string is empty.");
            return reviewList;
        }

        try {
            JSONObject reviewObject = new JSONObject(json);

            JSONArray resultsArray = reviewObject.optJSONArray(JSON_TAG_RESULTS);
            if (resultsArray != null) {
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject resultObject = resultsArray.optJSONObject(i);

                    String author = resultObject.optString(JSON_TAG_AUTHOR);
                    String content = resultObject.optString(JSON_TAG_CONTENT);
                    String url = resultObject.optString(JSON_TAG_URL);

                    MovieReview review = new MovieReview(author, content, url);
                    Log.d(TAG, "parseReviewJson, [" + i + "] " + review.toString());
                    reviewList.add(review);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }
}
