package com.udacity.popularmovies;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

class AutoFitGridLayoutManager extends GridLayoutManager {

    private static final String TAG = AutoFitGridLayoutManager.class.getSimpleName();

    private static final int DEFAULT_GRID_SPAN_COUNT = 2;

    private int mColumnWidth;

    AutoFitGridLayoutManager(Context context, int columnWidth) {
        super(context, DEFAULT_GRID_SPAN_COUNT);

        setColumnWidth(columnWidth);
    }

    private void setColumnWidth(int columnWidth) {
        if (columnWidth > 0 && columnWidth != mColumnWidth) {
            mColumnWidth = columnWidth;
        }
        updateSpanCount();
    }

    private void updateSpanCount() {
        int screenWidth = (getOrientation() == RecyclerView.VERTICAL) ? getWidth() : getHeight();
        int spanCount = Math.max(DEFAULT_GRID_SPAN_COUNT, screenWidth / mColumnWidth);

        Log.d(TAG, "updateSpanCount() spanCount = " + spanCount);
        setSpanCount(spanCount);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateSpanCount();

        super.onLayoutChildren(recycler, state);
    }
}
