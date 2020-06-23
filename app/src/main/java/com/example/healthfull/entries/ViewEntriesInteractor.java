package com.example.healthfull.entries;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ViewEntriesInteractor implements ViewEntriesContract.Interactor {

    private ViewEntriesContract.onLoadListener onLoadListener;

    public ViewEntriesInteractor(ViewEntriesContract.onLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    @Override
    public void performFirebaseEntriesLoad() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firestore
                .collection("users")
                .document(user.getUid())
                .collection("logs")
                .orderBy("date")
                .limitToLast(100)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //List<FoodEntry> entries = new ArrayList<>();

                    // first check if any results exist
                    if (task.getResult().isEmpty()) {
                        onLoadListener.onLoadSuccess(new ArrayList<>());
                    }

                    // Use a linked hash map to maintain order
                    Map<FoodEntry, Boolean> entries = new LinkedHashMap<>();

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        // retrieve the food document
                        DocumentReference food = (DocumentReference)doc.getData().get("food");

                        Date date = ((Timestamp)doc.getData().get("date")).toDate();
                        FoodEntry entry = new FoodEntry(food, date);
                        entries.put(entry, false);

                        FoodEntryRetriever retriever = new FoodEntryRetriever(entry);
                        retriever.setOnDoneListener(new OnDoneListener<FoodEntry>() {
                            @Override
                            public void onSuccess(FoodEntry object) {
                                entries.put(entry, true);
                                if (checkIfAllDone(entries)) {
                                    onLoadListener.onLoadSuccess(new ArrayList<FoodEntry>(entries.keySet()));
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                entry.setName(message);
                                entries.put(entry, true);
                                if (checkIfAllDone(entries)) {
                                    onLoadListener.onLoadFailure(message);
                                }
                            }
                        });
                        retriever.get();
                    }
                } else {
                    onLoadListener.onLoadFailure(task.getException().getMessage());
                }
            }
        });
    }

    private boolean checkIfAllDone(Map<FoodEntry, Boolean> entries) {
        for (Map.Entry<FoodEntry, Boolean> e : entries.entrySet()) {
            if (!e.getValue()) {
                return false;
            }
        }
        return true;
    }
}
