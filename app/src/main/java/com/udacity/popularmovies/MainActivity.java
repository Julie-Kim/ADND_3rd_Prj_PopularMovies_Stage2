package com.udacity.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.udacity.popularmovies.databinding.ActivityMainBinding;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.MovieJsonUtils;
import com.udacity.popularmovies.utilities.NetworkUtils;
import com.udacity.popularmovies.utilities.PreferenceUtils;
import com.udacity.popularmovies.utilities.UrlUtils;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int DEFAULT_POSTER_WIDTH = 540;

    private ActivityMainBinding mBinding;

    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, DEFAULT_POSTER_WIDTH);
        mBinding.rvMoviePoster.setLayoutManager(layoutManager);
        mBinding.rvMoviePoster.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mBinding.rvMoviePoster.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        showOrHideMovieData(true);

        String sortBy = UrlUtils.getSortByParam(this);
        Log.d(TAG, "loadMovieData() sortBy " + sortBy);

        new FetchMovieDataTask(this).execute(sortBy);
    }

    private void showOrHideMovieData(boolean show) {
        if (show) {
            mBinding.tvEmptyMessage.setVisibility(View.INVISIBLE);
            mBinding.rvMoviePoster.setVisibility(View.VISIBLE);
        } else {
            mBinding.rvMoviePoster.setVisibility(View.INVISIBLE);
            mBinding.tvEmptyMessage.setVisibility(View.VISIBLE);
        }
    }

    private void showOrHideLoadingIndicator(boolean show) {
        if (show) {
            mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        } else {
            mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_MOVIE, movie);

        startActivity(intent);
    }

    private static class FetchMovieDataTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        private WeakReference<MainActivity> mActivityReference;

        FetchMovieDataTask(MainActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = mActivityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e(TAG, "FetchMovieDataTask, onPreExecute() activity is null or is finishing.");
                return;
            }

            activity.showOrHideLoadingIndicator(true);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                Log.e(TAG, "FetchMovieDataTask, no sortBy parameter.");
                return null;
            }

            String sortBy = params[0];
            if (TextUtils.isEmpty(sortBy)) {
                Log.e(TAG, "FetchMovieDataTask, wrong sortBy parameter.");
                return null;
            }

            URL movieRequestUrl = UrlUtils.buildUrl(UrlUtils.GET_MOVIE, sortBy);

            String movieJsonResponse = NetworkUtils.getJsonResponse(movieRequestUrl);
            if (movieJsonResponse != null) {
                ArrayList<Movie> movieList = MovieJsonUtils.parseMovieJson(movieJsonResponse);
                Log.d(TAG, "FetchMovieDataTask, size of movieList: " + movieList.size());

                return movieList;
            } else {
                Log.e(TAG, "FetchMovieDataTask, No json response.");
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            MainActivity activity = mActivityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e(TAG, "FetchMovieDataTask, onPostExecute() activity is null or is finishing.");
                return;
            }

            activity.updateMovieData(movies);
        }
    }

    private void updateMovieData(ArrayList<Movie> movies) {
        showOrHideLoadingIndicator(false);

        if (movies != null && !movies.isEmpty()) {
            showOrHideMovieData(true);
            mMovieAdapter.setMovieData(movies);
        } else {
            showOrHideMovieData(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mMovieAdapter.setMovieData(new ArrayList<>());

                loadMovieData();
                return true;

            case R.id.action_sort_by:
                showSortBySelectionDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortBySelectionDialog() {
        int checkedItem = PreferenceUtils.getSortBySettingValue(this);
        Log.d(TAG, "showSortBySelectionDialog() checked item: " + checkedItem);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.action_sort_by)
                .setSingleChoiceItems(R.array.sort_by_setting_strings,
                        checkedItem,
                        (dialog1, which) -> {
                            Log.d(TAG, "showSortBySelectionDialog() clicked item: " + which);
                            PreferenceUtils.setSortBySettingValue(MainActivity.this, which);
                        })
                .setPositiveButton(R.string.ok, (dialog12, which) -> loadMovieData()).create().show();
    }
}