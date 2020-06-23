package com.example.healthfull.login;

import com.example.healthfull.util.MVPContract;

/**
 * LoginContract interface declares what methods are required across the MVP architectural activity
 */
public interface LoginContract extends MVPContract {
    /**
     * Methods the view must implement, usually callbacks from the Presenter
     */
    interface View {
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
        void onRegisterSuccess(String message);
        void onRegisterFailure(String message);
        void setInputEnabled(boolean enabled);
    }

    /**
     * Methods the presenter must implement
     */
    interface Presenter {
        void login(String email, String password);
    }

    /**
     * Methods the interactor must implement to interact with the model(s)
     */
    interface Interactor {
        /**
         * Attempt to login or create a user in Firebase
         * @param email User's email address
         * @param password User's account password
         */
        void performFirebaseLogin(String email, String password);
    }

    /**
     * Callback interface, from the Interactor to the Presenter
     */
    interface onLoginListener {
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
        void onRegisterSuccess(String message);
        void onRegisterFailure(String message);
    }
}
