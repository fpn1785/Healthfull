package com.example.healthfull.entries;

import java.util.List;

public class ViewEntriesPresenter implements ViewEntriesContract.Presenter, ViewEntriesContract.onLoadListener {

    private ViewEntriesContract.View view;
    private ViewEntriesContract.Interactor interactor;

    public ViewEntriesPresenter(ViewEntriesContract.View view) {
        this.view = view;
        this.interactor = new ViewEntriesInteractor(this);
    }

    @Override
    public void loadEntries() {
        view.showProgressBar(true);
        interactor.performFirebaseEntriesLoad();
    }

    @Override
    public void onLoadSuccess(List<FoodEntry> entries) {
        view.setEntriesViewAdapter(new ViewEntriesViewAdapter(entries));
        view.showProgressBar(false);
        view.onLoadSuccess(entries);
    }

    @Override
    public void onLoadFailure(String message) {
        view.showProgressBar(false);
    }
}
