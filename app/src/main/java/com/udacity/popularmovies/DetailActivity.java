package com.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.MovieDataUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String KEY_MOVIE = "movie";

    private static final String VOTE_TOTAL = "/10";

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

        updateMovieDetailInfo(movie);
    }

    private void updateMovieDetailInfo(Movie movie) {
        mOriginalTitle.setText(movie.getOriginalTitle());
        setMoviePoster(movie.getPosterPath());
        setMovieReleaseDate(movie.getReleaseDate());
        setVoteAverage(movie.getVoteAverage());
        mOverview.setText(movie.getOverview());
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
}
