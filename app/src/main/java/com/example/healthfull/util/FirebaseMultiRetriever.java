package com.example.healthfull.util;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseMultiRetriever {

    private class QueryData {
        Query query;
        Task<QuerySnapshot> task;
        boolean done;
        boolean success;

        QueryData(Query query) {
            this.query = query;
            this.done = false;
            this.success = false;
        }
    }

    private List<QueryData> queries;
    private OnDoneListener<List<Task<QuerySnapshot>>> onDoneListener;

    public FirebaseMultiRetriever(List<Query> queries) {
        this.queries = new ArrayList<>();
        for (Query q : queries) {
            this.queries.add(new QueryData(q));
        }
    }

    public void setOnDoneListener(OnDoneListener<List<Task<QuerySnapshot>>> onDoneListener) {
        this.onDoneListener = onDoneListener;
    }

    public void get() {
        for (QueryData q : queries) {
            q.query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    q.task = task;
                    q.done = true;
                    q.success = task.isSuccessful();
                    checkAllDone();
                }
            });
        }
    }

    private void checkAllDone() {
        boolean failed = false;

        List<Task<QuerySnapshot>> querySnapshots = new ArrayList<>();

        for (QueryData q : queries) {
            if (!q.done) {
                return;
            }
            if (!q.success) {
                failed = true;
                break;
            }
            querySnapshots.add(q.task);
        }

        // all tasks completed

        if (failed) {
            onDoneListener.onFailure("one or more tasks failed to complete successfully");
        } else {
            onDoneListener.onSuccess(querySnapshots);
        }
    }
}
