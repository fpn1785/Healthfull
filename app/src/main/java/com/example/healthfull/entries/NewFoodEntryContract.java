package com.example.healthfull.entries;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.search.FoodSearchResult;
import com.example.healthfull.util.MVPContract;

import java.util.List;

/**
 * NewFoodEntryContract interface declares what methods are required across the MVP architectural
 * activity
 */
public interface NewFoodEntryContract extends MVPContract {
    /**
     * Methods the view must implement, usually callbacks from the Presenter
     */
    interface View {
        void onSearchSuccess(List<FoodSearchResult> results);
        void onSearchFailure(String message);
        void onAddSuccess();
        void onAddFailure(String message);
        void setResultsViewAdapter(RecyclerView.Adapter adapter);
    }

    /**
     * Methods the presenter must implement
     */
    interface Presenter {
        void search(String query);
    }

    /**
     * Methods the interactor must implement to interact with the model(s)
     */
    interface Interactor {
        void performSearch(String query);
    }

    /**
     * Callback interface, from the Interactor to the Presenter
     */
    interface onAddFoodListener {
        void onSearchSuccess(List<FoodSearchResult> results);
        void onSearchFailure(String message);
        void onAddSuccess();
        void onAddFailure(String message);
    }
}
