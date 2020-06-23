package com.example.healthfull.entries;

import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;

public class ViewEntriesViewHolder extends RecyclerView.ViewHolder {
    private ConstraintLayout layout;
    private TextView dateAddedView;
    private TextView nameView;

    private DocumentReference log;

    public ViewEntriesViewHolder(ConstraintLayout layout) {
        super(layout);
        this.layout = ((ConstraintLayout)((CardView)layout.getChildAt(0)).getChildAt(0));
        dateAddedView = (TextView) this.layout.getChildAt(0);
        nameView = (TextView) this.layout.getChildAt(1);
        log = null;
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public TextView getDateAddedView() {
        return dateAddedView;
    }

    public TextView getNameView() {
        return nameView;
    }

    public DocumentReference getLog() {
        return log;
    }

    public void setLog(DocumentReference log) {
        this.log = log;
    }
}
