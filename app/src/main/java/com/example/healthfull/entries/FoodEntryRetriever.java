package com.example.healthfull.entries;

import androidx.annotation.NonNull;

import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class FoodEntryRetriever {

    private OnDoneListener<FoodEntry> onDoneListener;
    private FoodEntry food;

    public FoodEntryRetriever(FoodEntry food) {
        this.food = food;
    }

    /**
     * setOnDoneListener sets the listener callbacks which will be called on success or failure
     * @param onDoneListener the desired onDoneListener callbacks
     */
    public void setOnDoneListener(OnDoneListener<FoodEntry> onDoneListener) {
        this.onDoneListener = onDoneListener;
    }

    public void get() {
        food.getRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    food.setName(task.getResult().getData().get("name").toString());
                    onDoneListener.onSuccess(null);
                } else {
                    onDoneListener.onFailure(task.getException().getMessage());
                }
            }
        });
    }

}
