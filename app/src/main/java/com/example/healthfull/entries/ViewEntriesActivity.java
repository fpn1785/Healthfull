package com.example.healthfull.entries;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;

import java.util.List;

public class ViewEntriesActivity extends AppCompatActivity implements ViewEntriesContract.View {

    private ViewEntriesPresenter entriesPresenter;

    private ProgressBar progressBar;
    private RecyclerView entriesView;
    private LinearLayoutManager entriesLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        setTitle("Log History");

        // should start loading logs from firebase
        entriesPresenter = new ViewEntriesPresenter(this);

        progressBar = findViewById(R.id.view_entries_loading);
        entriesView = findViewById(R.id.view_entries_resultsContainer);
        entriesView.setHasFixedSize(true);

        entriesLayoutManager = new LinearLayoutManager(this);
        entriesLayoutManager.setReverseLayout(true);
        entriesLayoutManager.setStackFromEnd(true);
        entriesView.setLayoutManager(entriesLayoutManager);

        // do this last
        entriesPresenter.loadEntries();
    }

    @Override
    public void onLoadSuccess(List<FoodEntry> entries) {
        if (entries.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No entries yet. Add some from the dashboard", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setEntriesViewAdapter(RecyclerView.Adapter adapter) {
        entriesView.setAdapter(adapter);
    }

    @Override
    public void showProgressBar(boolean enabled) {
        progressBar.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }
}
