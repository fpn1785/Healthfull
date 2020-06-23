package com.example.healthfull.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.MainActivity;
import com.example.healthfull.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    private RegisterPresenter registerPresenter;

    private Calendar dateOfBirthCalendar;

    private EditText nameEditText;
    private EditText dateOfBirthEditText;

    private Button submitDetailsButton;

    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");

        dateOfBirthCalendar = Calendar.getInstance();

        nameEditText = findViewById(R.id.register_editTextName);
        dateOfBirthEditText = findViewById(R.id.register_editTextDateOfBirth);
        submitDetailsButton = findViewById(R.id.register_submitDetailsButton);
        progressBar = findViewById(R.id.register_loading);

        // setup the presenter
        registerPresenter = new RegisterPresenter(this);

        // setup date picker
        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateOfBirthCalendar.set(Calendar.YEAR, year);
                dateOfBirthCalendar.set(Calendar.MONTH, month);
                dateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateOfBirthEditText();
            }
        };

        dateOfBirthEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(
                        RegisterActivity.this,
                        datePickerListener,
                        dateOfBirthCalendar.get(Calendar.YEAR),
                        dateOfBirthCalendar.get(Calendar.MONTH),
                        dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        submitDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.submitDetails(
                        nameEditText.getText().toString(),
                        dateOfBirthCalendar.getTime()
                );
            }
        });
    }

    @Override
    public void setInputEnabled(boolean enabled) {
        nameEditText.setEnabled(enabled);
        dateOfBirthEditText.setEnabled(enabled);
        submitDetailsButton.setEnabled(enabled);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void updateDateOfBirthEditText() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        dateOfBirthEditText.setText(sdf.format(dateOfBirthCalendar.getTime()));
    }

    @Override
    public void onRegisterSuccess(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterFailure(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        setInputEnabled(true);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
