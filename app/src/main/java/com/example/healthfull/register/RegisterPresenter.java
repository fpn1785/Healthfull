package com.example.healthfull.register;

import java.util.Date;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.onSubmitListener {

    private RegisterContract.View view;
    private RegisterContract.Interactor interactor;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.interactor = new RegisterInteractor(this);
    }

    @Override
    public void submitDetails(String name, Date dateOfBirth) {
        view.setInputEnabled(false);
        interactor.performFirebaseSaveDetails(name, dateOfBirth);
    }

    @Override
    public void onSaveSuccess() {
        view.onRegisterSuccess("Successfully updated profile");
    }

    @Override
    public void onSaveFailure(String message) {
        view.setInputEnabled(true);
        view.onRegisterFailure(message);
    }
}
