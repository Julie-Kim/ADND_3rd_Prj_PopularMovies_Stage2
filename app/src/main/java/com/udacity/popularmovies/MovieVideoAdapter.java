package com.udacity.popularmovies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.popularmovies.databinding.VideoListItemBinding;
import com.udacity.popularmovies.model.MovieVideo;

import java.util.ArrayList;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.VideoAdapterViewHolder> {

    private static final String TAG = MovieVideoAdapter.class.getSimpleName();

    private final VideoAdapterOnClickHandler mClickHandler;

    public interface VideoAdapterOnClickHandler {
        void onClick(MovieVideo video);
    }

    private ArrayList<MovieVideo> mVideoList = new ArrayList<>();

    MovieVideoAdapter(VideoAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private VideoListItemBinding mBinding;

        VideoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieVideo video = mVideoList.get(getAdapterPosition());
            Log.d(TAG, "onClick(), clicked video key: " + video.getKey());

            mClickHandler.onClick(video);
        }
    }

    @NonNull
    @Override
    public VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_list_item, parent, false);
        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterViewHolder holder, int position) {
        MovieVideo video = mVideoList.get(position);
        String videoName = video.getName();
        Log.d(TAG, "onBindViewHolder() video name: " + videoName);

        holder.mBinding.tvVideoName.setText(videoName);
    }

    @Override
    public int getItemCount() {
        if (mVideoList.isEmpty()) {
            return 0;
        }
        return mVideoList.size();
    }

    void setVideoData(ArrayList<MovieVideo> videoList) {
        mVideoList.clear();
        mVideoList.addAll(videoList);

        notifyDataSetChanged();
    }
}
