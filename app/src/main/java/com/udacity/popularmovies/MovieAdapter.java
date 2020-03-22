package com.udacity.popularmovies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.database.MovieEntry;
import com.udacity.popularmovies.databinding.MovieGridItemBinding;
import com.udacity.popularmovies.utilities.MovieDataUtils;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieEntry movie);
    }

    private ArrayList<MovieEntry> mMovieList = new ArrayList<>();

    MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MovieGridItemBinding mBinding;

        MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(this);
        }

        void showOrHideLoadingIndicator(boolean show) {
            if (show) {
                mBinding.pbImageLoadingIndicator.setVisibility(View.VISIBLE);
            } else {
                mBinding.pbImageLoadingIndicator.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            MovieEntry movie = mMovieList.get(getAdapterPosition());
            Log.d(TAG, "onClick(), clicked movie: " + movie.getTitle() + ", _id: " + movie.getId());

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
        MovieEntry movie = mMovieList.get(position);
        byte[] posterImage = movie.getPosterImage();

        if (posterImage != null && posterImage.length > 0) {
            Log.d(TAG, "onBindViewHolder() load from image bitmap");

            holder.mBinding.ivPosterImage.setImageBitmap(MovieDataUtils.getBitmapFromBlob(posterImage));
            return;
        }

        String posterPath = MovieDataUtils.getMoviePosterFullPath(movie.getPosterPath());
        Log.d(TAG, "onBindViewHolder() posterPath: " + posterPath);

        holder.showOrHideLoadingIndicator(true);

        Picasso.get()
                .load(posterPath)
                .fit()
                .noPlaceholder()
                .error(R.drawable.error_image)
                .into(holder.mBinding.ivPosterImage, new Callback() {
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

        holder.mBinding.ivPosterImage.setContentDescription(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mMovieList.isEmpty()) {
            return 0;
        }
        return mMovieList.size();
    }

    void setMovieData(ArrayList<MovieEntry> movieList) {
        mMovieList.clear();
        mMovieList.addAll(movieList);

        notifyDataSetChanged();
    }
}
