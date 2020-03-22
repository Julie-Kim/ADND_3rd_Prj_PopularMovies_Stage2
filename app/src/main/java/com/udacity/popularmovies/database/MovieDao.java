package com.udacity.popularmovies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY _id")
    LiveData<List<MovieEntry>> loadAllMovies();

    @Insert
    void insertMovie(MovieEntry movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntry movieEntry);

    @Delete
    void deleteMovie(MovieEntry movieEntry);

    @Query("DELETE FROM movie WHERE movie_id = :movieId")
    void deleteByMovieId(String movieId);

    @Query("SELECT * FROM movie WHERE _id = :id")
    LiveData<MovieEntry> loadMovieById(int id);

    @Query("SELECT EXISTS(SELECT * FROM movie WHERE movie_id = :movieId LIMIT 1)")
    int existMovieByMovieId(String movieId);
}
