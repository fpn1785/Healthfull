package com.example.healthfull.entries;

import com.example.healthfull.util.OnDoneListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class FoodEntry {

    private DocumentReference ref;
    private String name, description;
    private float servingSize;
    private Date dateAdded;

    public FoodEntry() {
        this.ref = null;
        this.name = "";
        this.description = "";
        this.servingSize = 0;
        this.dateAdded = new Date();
    }

    public FoodEntry(DocumentReference ref, Date dateAdded) {
        this.ref = ref;
        this.name = "";
        this.description = "";
        this.servingSize = 0;
        this.dateAdded = dateAdded;
    }

    public FoodEntry(DocumentReference ref, String name, String description, float servingSize, Date dateAdded) {
        this.ref = ref;
        this.name = name;
        this.description = description;
        this.servingSize = servingSize;
        this.dateAdded = dateAdded;
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

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setServingSize(float servingSize) {
        this.servingSize = servingSize;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

}
