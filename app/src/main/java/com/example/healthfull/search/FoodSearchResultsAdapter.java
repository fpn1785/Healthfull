package com.example.healthfull.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;
import com.example.healthfull.util.OnViewHolderAddListener;

import java.util.List;

/**
 * FoodSearchResultsAdapter adapts a FoodSearchResults object to ViewHolders for display in a
 * RecyclerView
 */
public class FoodSearchResultsAdapter extends RecyclerView.Adapter<FoodSearchViewHolder> {

    private static final String TAG = "FoodSearchResults";

    private List<FoodSearchResult> foodSearchResults;

    private OnViewHolderAddListener onAddListener;

    /**
     * FoodSearchResultsAdapter creates ViewHolders from a FoodSearchResults object
     * @param foodSearchResults the foodSearchResults instance that the ViewHolders will be created
     *                          by
     * @param onAddListener the callback that will be called when a result add button is clicked
     */
    public FoodSearchResultsAdapter(List<FoodSearchResult> foodSearchResults, OnViewHolderAddListener onAddListener) {
        this.foodSearchResults = foodSearchResults;
        this.onAddListener = onAddListener;
    }

    @NonNull
    @Override
    public FoodSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate new view
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_searchresult, parent, false);

        FoodSearchViewHolder holder = new FoodSearchViewHolder(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodSearchViewHolder holder, int position) {
        //holder.getLayout().setTag(foodSearchResults.get(position).getId());

        holder.setFood(foodSearchResults.get(position).getRef());

        holder.getNameView().setText(foodSearchResults.get(position).getName());

        holder.getProgressBar().setVisibility(View.INVISIBLE);

        //holder.getAddButton().setTag(foodSearchResults.get(position).getId());

        holder.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddListener.onAdd(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodSearchResults.size();
    }
}
