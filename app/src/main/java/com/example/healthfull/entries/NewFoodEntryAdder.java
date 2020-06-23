package com.example.healthfull.entries;

import androidx.annotation.NonNull;

import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * NewFoodEntryAdder is an asynchronous class that has a short lifecycle to add a log to Firebase
 * for the currently logged in user
 */
public class NewFoodEntryAdder {

    private OnDoneListener onDoneListener;
    private DocumentReference food;

    /**
     * Constructor sets the food id (from Firebase) for the food that should be added
     */
    public NewFoodEntryAdder(DocumentReference food) {
        this.food = food;
    }

    /**
     * setOnDoneListener sets the listener callbacks which will be called on success or failure
     * @param onDoneListener the desired onDoneListener callbacks
     */
    public void setOnDoneListener(OnDoneListener onDoneListener) {
        this.onDoneListener = onDoneListener;
    }

    /**
     * saves the log to Firebase using the logged in user's id and current date and time
     * Calls the respective callback on save success or failure.
     * If calling the failure callback, the exception message will be passed through the message
     * parameter
     */
    public void save() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new IllegalStateException("There is no currently logged in user");
        }

        // add this to a user collection
        Map<String, Object> entry = new HashMap<>();
        entry.put("food", food);
        entry.put("date", new Date());

        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(FirebaseAuth
                        .getInstance()
                        .getCurrentUser()
                        .getUid()
                )
                .collection("logs")
                .document()
                .set(entry)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (onDoneListener != null) {
                                onDoneListener.onSuccess(null);
                            }
                        } else {
                            if (onDoneListener != null) {
                                onDoneListener.onFailure(task.getException().toString());
                            }
                        }
                    }
                });
    }
}
