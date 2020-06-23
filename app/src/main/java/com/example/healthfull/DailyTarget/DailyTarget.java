package com.example.healthfull.DailyTarget;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * @author Chandan Aulakh
 * Daily Targets class is for adding food and water targets to firestore database
 * This class also shows a record of all targets entered per user
 * There is an Oncreate method which finds the id of textView and editText to link XML to Class
 * There is an instance of Firebase which is retrived to ineract with firestore database
 */
public class DailyTarget extends AppCompatActivity {

    private static final String TAG = "DailyTarget";//to log errors

    private EditText editTextWaterTarget;//water target entry
    private EditText editTextFoodTarget;//food target entry
    private TextView textViewTarget;//display for targets

    //Firebase connection made and reference to "users" collection established
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference addTargetDoc = db.collection("users");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_target);
        //linking id variable
        editTextWaterTarget = findViewById(R.id.edit_textWaterTarget);
        editTextFoodTarget = findViewById(R.id.edit_textFoodTarget);
        textViewTarget = findViewById(R.id.textView_Targets);
    }

    /**
     * addFoodWaterTarget
     * takes input from user through app and then passes it as a string to save into
     * the firebase using a custom class at {@link TargetObject}
     * A toast is released if the target is saved successfully
     * A log is also made if the method is successful
     * @param view
     */
    public void addFoodWaterTarget(View view){
        String WaterTarget = editTextWaterTarget.getText().toString();
        String FoodTarget = editTextFoodTarget.getText().toString() + " calories";

        TargetObject target = new TargetObject(WaterTarget,FoodTarget);

        addTargetDoc.document("u1").collection("targets").add(target);
        Toast.makeText(this, "target saved", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"successful");
    }

    /**
     * loadTarget
     * This method navigates to the "targets" subc-ollection within a user document
     * It retrieves all documents(previous targets) from the "targets" sub-collection
     * A snapshot of the document is passed and is looped through to collect data
     * Data is then stored into variable and displayed through text view as a string
     * A toast is released on the screen if the target is loaded successfully
     * @param view
     */
    public void loadTarget(View view){
        addTargetDoc.document("u1")
                .collection("targets")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       String info = "";

                       for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
                           TargetObject target = documentSnapshots.toObject(TargetObject.class);

                           String water = target.getWaterTarget();
                           String food = target.getFoodTarget();

                           info += "water target: "+water+"\nfood target: "+food+"\n\n";
                       }
                        textViewTarget.setText(info);
                        Toast.makeText(DailyTarget.this, "Targets Loaded", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}






