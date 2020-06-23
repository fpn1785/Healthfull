package com.example.healthfull.entries;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * NewWaterEntryAdder is an asynchronous class that has a short lifecycle to add a log to Firebase
 * for the currently logged in user
 */
public class NewWaterEntryAdder extends NewFoodEntryAdder {

    /**
     * Constructor sets the id of Water (as per Firebase ID) for the entry adder
     */
    public NewWaterEntryAdder() {
        super(FirebaseFirestore.getInstance().document("food/uXRBMlRtnW9XMrZb0qUs"));
    }
}
