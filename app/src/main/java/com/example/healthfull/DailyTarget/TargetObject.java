package com.example.healthfull.DailyTarget;


/**
 * @author Chandan Aulakh
 * basic target Object to serve as input method into Firestore for {@link DailyTarget} class
 * has two private variables for water and food targets
 * This class is used as a template to store data retrieved from user
 */
public class TargetObject {

    private String waterTarget;
    private String foodTarget;

    /**
     * default constructor
     */
    public TargetObject(){

    }

    public TargetObject(String waterTarget, String foodTarget){
        this.waterTarget= waterTarget;
        this.foodTarget = foodTarget;
    }

    public String getWaterTarget() {
        return waterTarget;
    }

    public String getFoodTarget() {
        return foodTarget;
    }
}
