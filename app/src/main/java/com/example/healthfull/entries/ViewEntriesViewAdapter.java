package com.example.healthfull.entries;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ViewEntriesViewAdapter extends RecyclerView.Adapter<ViewEntriesViewHolder> {

    private List<FoodEntry> entries;

    public ViewEntriesViewAdapter(List<FoodEntry> entries) {
        this.entries = entries;
    }

    @NonNull
    @Override
    public ViewEntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_foodentry, parent, false);

        ViewEntriesViewHolder holder = new ViewEntriesViewHolder(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEntriesViewHolder holder, int position) {
        String dateFormat = "dd/MM/yyyy hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        holder.getDateAddedView().setText(sdf.format(entries.get(position).getDateAdded()));
        holder.getNameView().setText(entries.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}
