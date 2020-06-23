package com.example.healthfull;

import com.example.healthfull.util.MVPContract;

public interface MainContract extends MVPContract {
    interface View {
        void onAddWaterSuccess();
        void onAddWaterFailure(String message);
        void setAddWaterButtonAvailable(boolean enabled);
    }

    interface Presenter {
        void addWater();
    }

    interface Interactor {

    }

    interface onWaterAddedListener {
        void onWaterAddedSuccess();
        void onWaterAddedFailure(String message);
    }

}
