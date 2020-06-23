package com.example.healthfull.search;

import com.google.firebase.firestore.DocumentReference;

/**
 * FoodSearchResult contains data retrieved from Firebase for a specific food
 */
public class FoodSearchResult {

    private DocumentReference ref;
    private String name, description;
    private float servingSize;
    /*
    include all other nutritional information
     */

    /**
     * Default constructor
     */
    public FoodSearchResult() {
        this.ref = null;
        this.name = "";
        this.description = "";
        this.servingSize = 0;
    }

    /**
     * Constructor sets food id and name
     * @param ref Firebase document reference for the food
     * @param name name of the food
     */
    public FoodSearchResult(DocumentReference ref, String name) {
        this.ref = ref;
        this.name = name;
        this.description = "";
        this.servingSize = 0;
    }

    public DocumentReference getRef() {
        return ref;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getServingSize() {
        return servingSize;
    }
}
