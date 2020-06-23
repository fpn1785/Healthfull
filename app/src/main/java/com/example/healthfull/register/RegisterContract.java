package com.example.healthfull.register;

import com.example.healthfull.util.MVPContract;

import java.util.Date;

public interface RegisterContract extends MVPContract {
    /**
     * Methods the view must implement, usually callbacks from the Presenter
     */
    interface View {
        void onRegisterSuccess(String message);
        void onRegisterFailure(String message);

        void setInputEnabled(boolean enabled);
    }

    /**
     * Methods the presenter must implement
     */
    interface Presenter {
        void submitDetails(String name, Date dateOfBirth);
    }

    /**
     * Methods the interactor must implement to interact with the model(s)
     */
    interface Interactor {
        /**
         * Attempt to login or create a user in Firebase
         * @param name User's name
         * @param dateOfBirth User's date of birth
         */
        void performFirebaseSaveDetails(String name, Date dateOfBirth);
    }

    interface onSubmitListener {
        void onSaveSuccess();
        void onSaveFailure(String message);
    }
}
