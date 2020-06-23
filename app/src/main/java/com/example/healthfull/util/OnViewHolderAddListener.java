package com.example.healthfull.util;

import androidx.recyclerview.widget.RecyclerView;

/**
 * OnViewHolderAddListener is a callback interface that should be triggered when an action is
 * performed on a ViewHolder in data adapters for RecyclerViews
 */
public interface OnViewHolderAddListener {
    /**
     * onAdd is the callback which occurs when a ViewHolder is interacted with in a RecyclerView
     * @param viewHolder the ViewHolder in which the action was performed on
     */
    void onAdd(RecyclerView.ViewHolder viewHolder);
}
