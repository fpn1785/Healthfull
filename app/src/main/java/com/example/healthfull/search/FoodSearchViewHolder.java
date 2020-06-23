package com.example.healthfull.search;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;

/**
 * FoodSearchViewHolder contains the Layout and Views in a single FoodResult ViewHolder has
 * callbacks for adjusting feedback state
 */
public class FoodSearchViewHolder extends RecyclerView.ViewHolder {
    private ConstraintLayout layout;
    private TextView nameView;
    private Button addButton;
    private ProgressBar progressBar;

    private DocumentReference food;

    /**
     * Constructor retrieves relevant children from the layout
     * @param l
     */
    public FoodSearchViewHolder(ConstraintLayout l) {
        super(l);
        layout = l;
        nameView = (TextView) layout.getChildAt(0);
        addButton = (Button) layout.getChildAt(1);
        progressBar = (ProgressBar) layout.getChildAt(2);
        food = null;
    }

    /**
     * Callback to set feedback state with a progress bar replacing the button to show the operation
     * is pending
     */
    public void onAddButtonClick() {
        addButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Callback to return the ViewHolder to its default state to show the operation is complete
     */
    public void onAddFinished() {
        addButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public Button getAddButton() {
        return addButton;
    }

    public TextView getNameView() {
        return nameView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public DocumentReference getFood() {
        return food;
    }

    public void setFood(DocumentReference food) {
        this.food = food;
    }
}
