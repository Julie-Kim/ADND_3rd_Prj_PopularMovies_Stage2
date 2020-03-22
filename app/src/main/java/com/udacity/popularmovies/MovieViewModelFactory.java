package com.udacity.popularmovies;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.udacity.popularmovies.database.MovieDatabase;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieDatabase mDb;
    private final int mId;

    public MovieViewModelFactory(MovieDatabase database, int id) {
        mDb = database;
        mId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(mDb, mId);
    }
}