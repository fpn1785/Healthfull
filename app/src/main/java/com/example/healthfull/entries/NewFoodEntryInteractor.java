package com.example.healthfull.entries;

import android.util.Log;

import com.example.healthfull.search.FoodSearchResult;
import com.example.healthfull.util.FirebaseMultiRetriever;
import com.example.healthfull.util.OnDoneListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * MVP Interactor is responsible for controlling models the NewFoodEntry activity interacts with
 */
public class NewFoodEntryInteractor implements NewFoodEntryContract.Interactor {

    private static final String TAG = "NewFoodEntry";

    private NewFoodEntryContract.onAddFoodListener onAddFoodListener;

    public NewFoodEntryInteractor(NewFoodEntryContract.onAddFoodListener onAddFoodListener) {
        this.onAddFoodListener = onAddFoodListener;
    }

    // Migrate to functionality to search package using a callback for the onAddFoodListener
    // callback
    @Override
    public void performSearch(final String query) {
        // query food database
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Query q = firestore.collection("food").whereArrayContains("tags", query.toLowerCase());
        //Query q1 = firestore.collection("food").whereArrayContains("tags", "toast");

        List<Query> queries = new ArrayList<>();

        queries.add(q);
        //queries.add(q1);

        FirebaseMultiRetriever multi = new FirebaseMultiRetriever(queries);

        multi.setOnDoneListener(new OnDoneListener<List<Task<QuerySnapshot>>>() {
            @Override
            public void onSuccess(List<Task<QuerySnapshot>> object) {
                Task<QuerySnapshot> foodSearchTask = object.get(0);

                // get food search results
                List<FoodSearchResult> results = new ArrayList<>();
                for (QueryDocumentSnapshot doc : foodSearchTask.getResult()) {
                    results.add(new FoodSearchResult(doc.getReference(), doc.getData().get("name").toString()));
                }

//                Task<QuerySnapshot> toastSearchTask = object.get(1);
//
//                // get food search results
//                FoodSearchResults results2 = new FoodSearchResults();
//                for (QueryDocumentSnapshot doc : toastSearchTask.getResult()) {
//                    results2.add(new FoodSearchResult(doc.getId(), doc.getData().get("name").toString()));
//                }

                Log.e(TAG, "Objects returned: " + Integer.toString(object.size()));

                onAddFoodListener.onSearchSuccess(results);
            }

            @Override
            public void onFailure(String message) {
                onAddFoodListener.onSearchFailure(message);
            }
        });

        multi.get();

//        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    FoodSearchResults results = new FoodSearchResults();
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        results.add(new FoodSearchResult(doc.getId(), doc.getData().get("name").toString()));
//                    }
//                    onAddFoodListener.onSearchSuccess(results);
//                } else {
//                    onAddFoodListener.onSearchFailure(task.getException().toString());
//                }
//            }
//        });
    }
}
