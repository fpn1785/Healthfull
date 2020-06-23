package com.example.healthfull.entries;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.search.FoodSearchResult;
import com.example.healthfull.search.FoodSearchResultsAdapter;
import com.example.healthfull.search.FoodSearchViewHolder;
import com.example.healthfull.util.OnDoneListener;
import com.example.healthfull.util.OnViewHolderAddListener;

import java.util.List;

/**
 * MVP Presenter class for the NewFoodEntry activity
 */
public class NewFoodEntryPresenter implements NewFoodEntryContract.Presenter, NewFoodEntryContract.onAddFoodListener, OnViewHolderAddListener {
    private NewFoodEntryContract.View view;
    private NewFoodEntryContract.Interactor interactor;

    public NewFoodEntryPresenter(NewFoodEntryContract.View view) {
        this.view = view;
        this.interactor = new NewFoodEntryInteractor(this);
    }

    @Override
    public void search(String query) {
        interactor.performSearch(query);
    }

    @Override
    public void onSearchSuccess(List<FoodSearchResult> results) {
        view.setResultsViewAdapter(new FoodSearchResultsAdapter(results, this));
        view.onSearchSuccess(results);
    }

    @Override
    public void onSearchFailure(String message) {
        view.onSearchFailure(message);
    }

    @Override
    public void onAddSuccess() {
        view.onAddSuccess();
    }

    @Override
    public void onAddFailure(String message) {
        view.onAddFailure(message);
    }

    @Override
    public void onAdd(RecyclerView.ViewHolder viewHolder) {
        FoodSearchViewHolder holder = (FoodSearchViewHolder) viewHolder;
        holder.onAddButtonClick();
        NewFoodEntryAdder adder = new NewFoodEntryAdder(holder.getFood());
        adder.setOnDoneListener(new OnDoneListener() {
            @Override
            public void onSuccess(Object object) {
                holder.onAddFinished();
                onAddSuccess();
            }

            @Override
            public void onFailure(String message) {
                onAddFailure(message);
            }
        });
        adder.save();
    }
}
