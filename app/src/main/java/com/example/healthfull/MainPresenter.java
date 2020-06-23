package com.example.healthfull;

import com.example.healthfull.entries.NewWaterEntryAdder;
import com.example.healthfull.util.OnDoneListener;

public class MainPresenter implements MainContract.Presenter, MainContract.onWaterAddedListener{

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void addWater() {
        view.setAddWaterButtonAvailable(false);
        NewWaterEntryAdder adder = new NewWaterEntryAdder();
        adder.setOnDoneListener(new OnDoneListener() {
            @Override
            public void onSuccess(Object object) {
                onWaterAddedSuccess();
            }

            @Override
            public void onFailure(String message) {
                onWaterAddedFailure(message);
            }
        });
        adder.save();
    }

    @Override
    public void onWaterAddedSuccess() {
        view.onAddWaterSuccess();
        view.setAddWaterButtonAvailable(true);
    }

    @Override
    public void onWaterAddedFailure(String message) {
        view.onAddWaterFailure(message);
        view.setAddWaterButtonAvailable(true);
    }
}
