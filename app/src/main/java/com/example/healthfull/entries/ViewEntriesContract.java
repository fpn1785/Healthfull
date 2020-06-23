package com.example.healthfull.entries;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.util.MVPContract;

import java.util.List;

public interface ViewEntriesContract extends MVPContract {

    interface View {
        void onLoadSuccess(List<FoodEntry> entries);
        void setEntriesViewAdapter(RecyclerView.Adapter adapter);
        void showProgressBar(boolean enabled);
    }

    interface Presenter {
        void loadEntries();
    }

    interface Interactor {
        void performFirebaseEntriesLoad();
    }

    interface onLoadListener {
        void onLoadSuccess(List<FoodEntry> entries);
        void onLoadFailure(String message);
    }
}
