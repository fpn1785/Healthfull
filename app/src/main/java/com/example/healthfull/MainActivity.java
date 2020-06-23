package com.example.healthfull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.DailyTarget.DailyTarget;
import com.example.healthfull.RewardsSystem.Rewards;
import com.example.healthfull.entries.ViewEntriesActivity;
import com.example.healthfull.login.LoginActivity;
import com.example.healthfull.profile.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.healthfull.entries.NewFoodEntryActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    public static final int CAMERA_REQUEST_CODE = 102;

    private static final String TAG = "Main";

    MainContract.Presenter presenter;

    //for camera
    ImageView selectedImage;
    ImageView galleryImageView;
    ImageButton addEntryButton;

    Button profileButton;
    Button cameraButton;
    Button rewardButton;
    Button galleryButton;
    Button goalButton;
    Button viewEntriesButton;
    Button addWaterButton;
    ProgressBar addWaterProgressBar;
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference mCollectionReference = mFirebaseFirestore.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();

        Log.d("FCM", "Default value");

        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d("FCM", "Key: " + key + " Value: " + value);
            }
            Log.d("FCM", "Not null");
        }

        presenter = new MainPresenter(this);

        TextView welcomeTitle = findViewById(R.id.welcomeUserTextView);
        welcomeTitle.setText("Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        galleryImageView = findViewById(R.id.galleryImageView);
        //selectedImage = findViewById(R.id.displayImageView);
        cameraButton = findViewById(R.id.cameraButton);

        profileButton = findViewById(R.id.main_profilebutton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        galleryButton = findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent - this is what we want to happen, android is going to try to make that happen
                Intent startIntent = new Intent(getApplicationContext(), Gallery.class);

                //how to parse info to the another activity
                //this sends extra info to another activity as a bundle and the other activity can
                //unbundle this info and use it
                startIntent.putExtra("com.example.quicklauncher.SOMETHING", "HELLO WORLD!");
                startActivity(startIntent);
            }
        });


        addEntryButton = findViewById(R.id.main_addButton);
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewFoodEntryActivity.class);
                startActivity(intent);
            }
        });

        viewEntriesButton = findViewById(R.id.main_viewentries_button);
        viewEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewEntriesActivity.class);
                startActivity(intent);
            }
        });

        addWaterButton = findViewById(R.id.main_addwater_button);
        addWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addWater();
            }
        });

        // add water progress bar
        addWaterProgressBar = findViewById(R.id.main_addwater_progressbar);
        addWaterProgressBar.setVisibility(View.INVISIBLE);

        goalButton = findViewById(R.id.buttonGoal);
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyTarget.class);
                startActivity(intent);
            }
        });

        rewardButton = findViewById(R.id.rewardButton);
        rewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Rewards.class);
                startActivity(intent);
            }
        });

        if(mFirebaseUser != null) {
            final String uid = mFirebaseUser.getUid();

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast
                            String msg = getString(R.string.msg_token_fmt, token);
                            Log.d(TAG, msg);

                            Map<String, String> userInfo = new HashMap<>();
                            userInfo.put("email", mFirebaseUser.getEmail());
                            userInfo.put("firebase_instance_id", token);

                            mCollectionReference.document(uid).set(userInfo, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            CollectionReference mCollectionReference2 = FirebaseFirestore.getInstance().collection("id-collections");
                            Map<String, Boolean> user_id = new HashMap<>();
                            user_id.put(uid, true);
                            mCollectionReference2.document("user-ids").set(user_id, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

    @Override
    public void onAddWaterSuccess() {
        Toast.makeText(getApplicationContext(), "Water added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddWaterFailure(String message) {
        Log.e(TAG, message);
        Toast.makeText(getApplicationContext(), "Failed to add water, please try again later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setAddWaterButtonAvailable(boolean enabled) {
        addWaterProgressBar.setVisibility(enabled ? View.INVISIBLE : View.VISIBLE);
        addWaterButton.setEnabled(enabled);
    }


}
