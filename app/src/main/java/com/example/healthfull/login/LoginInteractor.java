package com.example.healthfull.login;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

/**
 * MVP Interactor is responsible for controlling models the login activity interacts with
 */
public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.onLoginListener onLoginListener;

    public LoginInteractor(LoginContract.onLoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    @Override
    public void performFirebaseLogin(final String email, final String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLoginListener.onLoginSuccess("Logged In Successfully");
                        } else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthInvalidUserException e) {
                                // User does not exist,
                                createUser(email, password);
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                // Invalid password, user exists
                                onLoginListener.onLoginFailure("Password Incorrect");
                            } catch(Exception e) {
                                Log.e("Healthfull App", e.getMessage());
                            }
                        }
                    }
                });
    }

    private void createUser(String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onLoginListener.onRegisterSuccess("Registered Successfully");
                        } else {
                            onLoginListener.onRegisterFailure(task.getException().toString());
                        }
                    }
                });
    }
}
