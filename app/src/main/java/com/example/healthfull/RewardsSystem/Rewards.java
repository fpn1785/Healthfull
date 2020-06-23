package com.example.healthfull.RewardsSystem;


/**
 * @author Chandan Aulakh
 *Rewards class has 3 main functions: calculating goal calories, redeeming points and getting recipes.
 * The OnCreate links using find id so that  XML and java class may interact when user input
 * is given.
 * The class is responsible for storing user points in the firestore database and
 * to retrieve a random recipe for when the user chooses to redeem a recipe from the database.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfull.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class Rewards extends AppCompatActivity{

    private static final String TAG = "Rewards";

    private static double goalCalorie;
    private static double userCalorie = 2500;
    private TextView textViewRecipe;
    private EditText textWeight;
    private EditText textHeight;
    private EditText textAge;

    //Random number generator for use with the getRecipe method
    private static Random randNum = new Random();
    private static int ran = randNum.nextInt(4);
    private static String random = Integer.toString(ran);

    //instance of firebase retrieved, reference to "recipes" collection and reference to "u1" document
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipesRef = db.collection("recipes");
    private DocumentReference userPoint = db.collection("users").document("u1");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        textViewRecipe = findViewById(R.id.view_recipe_data);
        textWeight = findViewById(R.id.edit_textWeight);
        textHeight = findViewById(R.id.edit_textHeight);
        textAge = findViewById(R.id.edit_textAge);
    }

    /**
     * setGoalCalories
     * takes user input for height, weight and age to calculate goal calories required based
     * off the Harris-Benedict formula.
     * A toast is released upon successful calculation of the goal calories
     * The goalCalorie is then returned as a double value
     * @param view
     * @return
     */
    public double setGoalCalories(View view){
        String inputWeight = textWeight.getText().toString();
        String inputHeight = textHeight.getText().toString();
        String inputAge = textAge.getText().toString();

        double weight  = Double.parseDouble(inputWeight);
        double height = Double.parseDouble(inputHeight);
        double age = Double.parseDouble(inputAge);

        goalCalorie= 1.2*(66.5 + (13.75*weight)+(5.003*height)-(6.775*age));
        Toast.makeText(this, "goal calories set", Toast.LENGTH_SHORT).show();
        return goalCalorie;
    }

    /**
     * compareCalories
     * simple class for checking if user calories is greater than goal calories, returns true if it
     * is.
     * @return
     */
    public boolean compareCalories(){
        if(userCalorie>goalCalorie){
            return true;
        }
        return false;
    }

    /**
     * subtractPoints
     * this method is used at the end of getRecipes
     * once the user has redeemed a random recipe, 50 points are subtracted from user
     * points. The "points" field is decremented by 50
     */
    //subtractPoints reduces the user points by 50 after recipe redemption
    public void subtractPoints(){
        userPoint.update("points", FieldValue.increment(-50));
    }

    /**
     * redeemPoints
     * This method uses the compareCalories method.
     * If compareCalories returns true, the method will increment user "points" field by 10 points
     * A toast will be released on successful increment alongside a Log for success
     * If compareCalories returns false, the user will receive a toast message for failure.
     * The log will also record that points were not incremented
     * @param view
     */
    //user clicks to redeem points for the day, if calories are higher than goal calories
    public void redeemPoints(View view){
        if(compareCalories()) {
            userPoint.update("points", FieldValue.increment(10))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Rewards.this, "you received 10 points", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "redeem points successful");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Rewards.this, "you did not meet your calorie goal", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"could not redeem points");
                }
            });
        }
    }

    /**
     * a reference to the recipes collection is made
     * a query is used to return all recipes with the "random" id number.
     * The number of recipes is already known, therefore random range is controlled
     * After retrieving the document a snapshot is taken and the data is looped through.
     * The data is then assigned to variables of the {@link RecipeObject} class
     * These variables are then assigned to a string and displayed through textView
     * On success, a toast is released with "recipe loaded"
     * At the end 50 points are decremented of the user, using subtractPoints method.
     * @param view
     */
    //getRecipe retrieves a recipe if user has points, recipe is randomised using randomInt
    public void getRecipe(View view){
            recipesRef
                    .whereEqualTo("id",random)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String display="";

                            for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
                                RecipeObject rec = documentSnapshots.toObject(RecipeObject.class);

                                String id = rec.getId();
                                String ingredients = rec.getIngredients();
                                String name = rec.getName();
                                String recipe = rec.getRecipe();

                                display += "id: "+id+"\n\ningredients: "+ingredients+"\n\nname: "+name
                                        +"\n\nrecipe: "+recipe+"\n\n\n\n";
                            }
                            textViewRecipe.setText(display);
                            Toast.makeText(Rewards.this, "Recipe Loaded", Toast.LENGTH_SHORT).show();
                        }
                    });
            subtractPoints();
    }

}

