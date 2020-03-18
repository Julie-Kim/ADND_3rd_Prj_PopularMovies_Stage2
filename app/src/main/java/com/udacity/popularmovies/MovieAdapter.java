package com.udacity.popularmovies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utilities.MovieDataUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    private ArrayList<Movie> mMovieList = new ArrayList<>();

    MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_poster_image)
        ImageView mMovieImageView;

        @BindView(R.id.pb_image_loading_indicator)
        ProgressBar mImageLoadingIndicator;

        MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void showOrHideLoadingIndicator(boolean show) {
            if (show) {
                mImageLoadingIndicator.setVisibility(View.VISIBLE);
            } else {
                mImageLoadingIndicator.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            Movie movie = mMovieList.get(getAdapterPosition());
            Log.d(TAG, "onClick(), clicked movie: " + movie.getTitle());

            mClickHandler.onClick(movie);
        }
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_grid_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        String posterPath = MovieDataUtils.getMoviePosterFullPath(movie.getPosterPath());
        Log.d(TAG, "onBindViewHolder() posterPath: " + posterPath);

        holder.showOrHideLoadingIndicator(true);

        Picasso.get()
                .load(posterPath)
                .fit()
                .noPlaceholder()
                .error(R.drawable.error_image)
                .into(holder.mMovieImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.showOrHideLoadingIndicator(false);
                            }

                            @Override
                            public void onError(Exception e) {
                                holder.showOrHideLoadingIndicator(false);
                            }
                        }
                );

        holder.mMovieImageView.setContentDescription(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mMovieList.isEmpty()) {
            return 0;
        }
        return mMovieList.size();
    }

    void setMovieData(ArrayList<Movie> movieList) {
        mMovieList.clear();
        mMovieList.addAll(movieList);

        notifyDataSetChanged();
    }
}
