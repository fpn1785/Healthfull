package com.example.healthfull.login;

/**
 * MVP Presenter class for the Login activity
 */
public class LoginPresenter implements LoginContract.Presenter, LoginContract.onLoginListener {

    private LoginContract.View view;
    private LoginContract.Interactor loginInteractor;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.loginInteractor = new LoginInteractor(this);
    }

    public void login(String email, String password) {
        view.setInputEnabled(false);
        loginInteractor.performFirebaseLogin(email, password);
    }

    @Override
    public void onLoginSuccess(String message) {
        view.onLoginSuccess(message);
    }

    @Override
    public void onLoginFailure(String message) {
        view.setInputEnabled(true);
        view.onLoginFailure(message);
    }

    @Override
    public void onRegisterSuccess(String message) {
        view.onRegisterSuccess(message);
    }

    @Override
    public void onRegisterFailure(String message) {
        view.onRegisterFailure(message);
    }
}
