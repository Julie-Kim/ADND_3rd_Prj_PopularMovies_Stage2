package com.udacity.popularmovies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.popularmovies.database.MovieDatabase;
import com.udacity.popularmovies.database.MovieEntry;

public class MovieViewModel extends ViewModel {

    private LiveData<MovieEntry> mMovie;

    public MovieViewModel(MovieDatabase database, int id) {
        mMovie = database.movieDao().loadMovieById(id);
    }

    public LiveData<MovieEntry> getMovie() {
        return mMovie;
    }
}
