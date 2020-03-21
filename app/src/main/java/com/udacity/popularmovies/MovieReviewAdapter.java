package com.udacity.popularmovies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.model.MovieReview;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewAdapterViewHolder> {

    private static final String TAG = MovieReviewAdapter.class.getSimpleName();

    private final ReviewAdapterOnClickHandler mClickHandler;

    public interface ReviewAdapterOnClickHandler {
        void onClick(MovieReview review);
    }

    private ArrayList<MovieReview> mReviewList = new ArrayList<>();

    public MovieReviewAdapter(ReviewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_review_content)
        TextView mReviewContentText;

        ReviewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieReview review = mReviewList.get(getAdapterPosition());
            Log.d(TAG, "onClick() clicked review content: " + review.getContent().substring(0, 20));

            mClickHandler.onClick(review);
        }
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        MovieReview review = mReviewList.get(position);
        String reviewContent = review.getContent();
        Log.d(TAG, "onBindViewHolder() review content: " + reviewContent.substring(0, 20));

        holder.mReviewContentText.setText(reviewContent);
    }

    @Override
    public int getItemCount() {
        if (mReviewList.isEmpty()) {
            return 0;
        }
        return mReviewList.size();
    }

    void setReviewData(ArrayList<MovieReview> reviewList) {
        mReviewList.clear();
        mReviewList.addAll(reviewList);

        notifyDataSetChanged();
    }
}
