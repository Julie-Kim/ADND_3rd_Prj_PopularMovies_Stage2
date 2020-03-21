package com.udacity.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.model.MovieReview;
import com.udacity.popularmovies.model.MovieVideo;
import com.udacity.popularmovies.utilities.MovieDataUtils;
import com.udacity.popularmovies.utilities.MovieJsonUtils;
import com.udacity.popularmovies.utilities.NetworkUtils;
import com.udacity.popularmovies.utilities.UrlUtils;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity
        implements MovieVideoAdapter.VideoAdapterOnClickHandler,
        MovieReviewAdapter.ReviewAdapterOnClickHandler {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String KEY_MOVIE = "movie";

    private static final String VOTE_TOTAL = " / 10";

    private static final String YOUTUBE_PACKAGE = "com.google.android.youtube";

    @BindView(R.id.tv_original_title)
    TextView mOriginalTitle;

    @BindView(R.id.iv_poster_image)
    ImageView mPosterImage;

    @BindView(R.id.pb_image_loading_indicator)
    ProgressBar mImageLoadingIndicator;

    @BindView(R.id.tv_released_year)
    TextView mReleasedYear;

    @BindView(R.id.tv_vote_average)
    TextView mVoteAverage;

    @BindView(R.id.tv_overview)
    TextView mOverview;

    @BindView(R.id.videos_divider)
    View mVideosDivider;

    @BindView(R.id.tv_videos_title)
    TextView mVideosTitle;

    @BindView(R.id.rv_videos)
    RecyclerView mVideosRecyclerView;

    @BindView(R.id.reviews_divider)
    View mReviewsDivider;

    @BindView(R.id.tv_reviews_title)
    TextView mReviewsTitle;

    @BindView(R.id.rv_reviews)
    RecyclerView mReviewsRecyclerView;

    private MovieVideoAdapter mVideoAdapter;
    private MovieReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "onCreate() intent is null.");
            return;
        }

        if (!intent.hasExtra(KEY_MOVIE)) {
            Log.e(TAG, "onCreate() no movie intent data.");
            return;
        }

        Movie movie = intent.getParcelableExtra(KEY_MOVIE);
        if (movie == null) {
            Log.e(TAG, "onCreate() movie data is null.");
            return;
        }

        initVideoRecyclerView();
        initReviewRecyclerView();

        updateMovieDetailInfo(movie);
    }

    private void initReviewRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mReviewsRecyclerView.setLayoutManager(layoutManager);
        mReviewsRecyclerView.setHasFixedSize(true);

        mReviewAdapter = new MovieReviewAdapter(this);
        mReviewsRecyclerView.setAdapter(mReviewAdapter);
        mReviewsRecyclerView.addItemDecoration(getDividerItemDecoration());
    }

    private void initVideoRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mVideosRecyclerView.setLayoutManager(layoutManager);
        mVideosRecyclerView.setHasFixedSize(true);

        mVideoAdapter = new MovieVideoAdapter(this);
        mVideosRecyclerView.setAdapter(mVideoAdapter);
        mVideosRecyclerView.addItemDecoration(getDividerItemDecoration());
    }

    private CustomDividerItemDecoration getDividerItemDecoration() {
        return new CustomDividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.recyclerview_divider));
    }

    private void updateMovieDetailInfo(Movie movie) {
        mOriginalTitle.setText(movie.getOriginalTitle());
        setMoviePoster(movie.getPosterPath());
        setMovieReleaseDate(movie.getReleaseDate());
        setVoteAverage(movie.getVoteAverage());
        mOverview.setText(movie.getOverview());

        loadVideoData(movie.getId());
        loadReviewData(movie.getId());
    }

    private void loadVideoData(String movieId) {
        new FetchVideoDataTask(this).execute(movieId);
    }

    private void loadReviewData(String movieId) {
        new FetchReviewDataTask(this).execute(movieId);
    }

    private void setMoviePoster(String posterPath) {
        String fullPath = MovieDataUtils.getMoviePosterFullPath(posterPath);
        Log.d(TAG, "setMoviePoster() fullPath: " + fullPath);

        showOrHideLoadingIndicator(true);

        Picasso.get()
                .load(fullPath)
                .fit()
                .noPlaceholder()
                .error(R.drawable.error_image)
                .into(mPosterImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                showOrHideLoadingIndicator(false);
                            }

                            @Override
                            public void onError(Exception e) {
                                showOrHideLoadingIndicator(false);
                            }
                        }
                );
    }

    private void showOrHideLoadingIndicator(boolean show) {
        if (show) {
            mImageLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mImageLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    private void setMovieReleaseDate(String releaseDate) {
        try {
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            calendar.setTime(Objects.requireNonNull(formatter.parse(releaseDate)));

            releaseDate = String.valueOf(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mReleasedYear.setText(releaseDate);
    }

    private void setVoteAverage(float voteAverage) {
        String vote = voteAverage + VOTE_TOTAL;
        mVoteAverage.setText(vote);
    }

    @Override
    public void onClick(MovieVideo video) {
        Uri videoUri = MovieDataUtils.getYoutubeUri(video.getKey());
        Intent intent;

        if (isYoutubeInstalled()) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage(YOUTUBE_PACKAGE);
            intent.setData(videoUri);
        } else {
            intent = new Intent(Intent.ACTION_VIEW, videoUri);
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private boolean isYoutubeInstalled() {
        return getPackageManager().getLaunchIntentForPackage(YOUTUBE_PACKAGE) != null;
    }

    @Override
    public void onClick(MovieReview review) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private static class FetchVideoDataTask extends AsyncTask<String, Void, ArrayList<MovieVideo>> {

        private WeakReference<DetailActivity> mActivityReference;

        FetchVideoDataTask(DetailActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        protected ArrayList<MovieVideo> doInBackground(String... params) {
            if (params.length == 0) {
                Log.e(TAG, "FetchVideoDataTask, no movieId parameter.");
                return null;
            }

            String movieId = params[0];
            if (TextUtils.isEmpty(movieId)) {
                Log.e(TAG, "FetchVideoDataTask, wrong movieId parameter.");
                return null;
            }

            URL videoRequestUrl = UrlUtils.buildUrl(UrlUtils.GET_VIDEO, movieId);

            String videoJsonResponse = NetworkUtils.getJsonResponse(videoRequestUrl);
            if (videoJsonResponse != null) {
                ArrayList<MovieVideo> videoList = MovieJsonUtils.parseVideoJson(videoJsonResponse);
                Log.d(TAG, "FetchVideoDataTask, size of videoList: " + videoList.size());

                return videoList;
            } else {
                Log.e(TAG, "FetchVideoDataTask, No json response.");
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieVideo> videos) {
            super.onPostExecute(videos);

            DetailActivity activity = mActivityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e(TAG, "FetchVideoDataTask, onPostExecute() activity is null or is finishing.");
                return;
            }

            activity.updateVideoData(videos);
        }
    }

    private void updateVideoData(ArrayList<MovieVideo> videos) {
        if (videos == null || videos.isEmpty()) {
            showOrHideVideosView(false);
            return;
        }

        showOrHideVideosView(true);
        mVideoAdapter.setVideoData(videos);
    }

    private void showOrHideVideosView(boolean show) {
        if (show) {
            mVideosDivider.setVisibility(View.VISIBLE);
            mVideosTitle.setVisibility(View.VISIBLE);
        } else {
            mVideosDivider.setVisibility(View.GONE);
            mVideosTitle.setVisibility(View.GONE);
        }
    }

    private static class FetchReviewDataTask extends AsyncTask<String, Void, ArrayList<MovieReview>> {

        private WeakReference<DetailActivity> mActivityReference;

        FetchReviewDataTask(DetailActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        protected ArrayList<MovieReview> doInBackground(String... params) {
            if (params.length == 0) {
                Log.e(TAG, "FetchReviewDataTask, no movieId parameter.");
                return null;
            }

            String movieId = params[0];
            if (TextUtils.isEmpty(movieId)) {
                Log.e(TAG, "FetchReviewDataTask, wrong movieId parameter.");
                return null;
            }

            URL reviewRequestUrl = UrlUtils.buildUrl(UrlUtils.GET_REVIEW, movieId);

            String reviewJsonResponse = NetworkUtils.getJsonResponse(reviewRequestUrl);
            if (reviewJsonResponse != null) {
                ArrayList<MovieReview> reviewList = MovieJsonUtils.parseReviewJson(reviewJsonResponse);
                Log.d(TAG, "FetchReviewDataTask, size of reviewList: " + reviewList.size());

                return reviewList;
            } else {
                Log.e(TAG, "FetchReviewDataTask, No json response.");
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieReview> reviews) {
            super.onPostExecute(reviews);

            DetailActivity activity = mActivityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e(TAG, "FetchReviewDataTask, onPostExecute() activity is null or is finishing.");
                return;
            }

            activity.updateReviewData(reviews);
        }
    }

    private void updateReviewData(ArrayList<MovieReview> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            showOrHideReviewView(false);
            return;
        }

        showOrHideReviewView(true);
        mReviewAdapter.setReviewData(reviews);
    }

    private void showOrHideReviewView(boolean show) {
        if (show) {
            mReviewsDivider.setVisibility(View.VISIBLE);
            mReviewsTitle.setVisibility(View.VISIBLE);
        } else {
            mReviewsDivider.setVisibility(View.GONE);
            mReviewsTitle.setVisibility(View.GONE);
        }
    }
}
