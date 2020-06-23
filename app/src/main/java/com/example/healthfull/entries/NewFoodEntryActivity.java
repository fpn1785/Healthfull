package com.example.healthfull.entries;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfull.R;
import com.example.healthfull.search.FoodSearchResult;

import java.util.List;

/**
 * MVP View class for the NewFoodEntry activity
 */
public class NewFoodEntryActivity extends AppCompatActivity implements NewFoodEntryContract.View {

    private static final String TAG = "NewFoodEntry";

    private NewFoodEntryPresenter presenter;

    private EditText searchInput;
    private Button searchButton;
    private ProgressBar progressBar;
    private RecyclerView resultsView;
    private RecyclerView.LayoutManager resultsLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfoodentry);

        setTitle("New Food Log");

        presenter = new NewFoodEntryPresenter(this);

        searchInput = findViewById(R.id.food_entry_query);
        searchButton = findViewById(R.id.food_entry_searchButton);
        progressBar = findViewById(R.id.food_entry_loading);
        resultsView = findViewById(R.id.food_entry_resultsContainer);

        resultsView.setHasFixedSize(true);

        resultsLayoutManager = new LinearLayoutManager(this);
        resultsView.setLayoutManager(resultsLayoutManager);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                presenter.search(searchInput.getText().toString());
            }
        });

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    progressBar.setVisibility(View.VISIBLE);
                    presenter.search(searchInput.getText().toString());
                }
                return false;
            }
        });
    }

    @Override
    public void onSearchSuccess(List<FoodSearchResult> results) {
        progressBar.setVisibility(View.INVISIBLE);

        if (results.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No Results Found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSearchFailure(String message) {
        Log.e(TAG, message);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), getString(R.string.food_entry_query_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccess() {
        Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddFailure(String message) {
        Log.e(TAG, message);
        Toast.makeText(getApplicationContext(), "An error occurred while adding", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setResultsViewAdapter(RecyclerView.Adapter adapter) {
        resultsView.setAdapter(adapter);
    }
}
