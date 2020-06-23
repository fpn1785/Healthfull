package com.example.healthfull.register;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterInteractor implements RegisterContract.Interactor {

    private RegisterContract.onSubmitListener onSubmitListener;

    public RegisterInteractor(RegisterContract.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    @Override
    public void performFirebaseSaveDetails(String name, Date dateOfBirth) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        boolean[] queriesCompleted= new boolean[]{ false, false };

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // handle multiple successes
                            queriesCompleted[0] = true;
                            checkSaveSucceeded(queriesCompleted);
                        }
                    }
                });

        // update users date of birth in the database

        // add this to a user collection
        Map<String, Object> profileEntry = new HashMap<>();
        profileEntry.put("dateOfBirth", dateOfBirth);
        profileEntry.put("name", name);

        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(user.getUid())
                .set(profileEntry)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // handle multiple successes
                            queriesCompleted[1] = true;
                            checkSaveSucceeded(queriesCompleted);
                        }
                    }
                });
    }

    private void checkSaveSucceeded(boolean[] queries) {
        for (boolean q : queries) {
            if (!q) {
                return;
            }
        }

        // all succeeded
        onSubmitListener.onSaveSuccess();
    }
}
