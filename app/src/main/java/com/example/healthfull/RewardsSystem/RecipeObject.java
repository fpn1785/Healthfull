package com.example.healthfull.RewardsSystem;

/**
 * @author Chandan Aulakh
 * RecipeObject serves as a class for storing the data gained from a document snapshot in
 * {@link Rewards} class.
 * This data in the variables is then displayed as a string in textView on the app
 * The app has basic private variables for id, ingredients, name and recipe for a document
 * in the recipes collection.
 */
public class RecipeObject {
    private String id;
    private String ingredients;
    private String name;
    private String recipe;

    /**
     * default constructor
     */
    public RecipeObject(){
        //public constructor
    }

    public RecipeObject(String id, String ingredients, String name, String recipe){
        this.id = id;
        this.ingredients = ingredients;
        this.name = name;
        this.recipe=recipe;
    }

    public String getId() {
        return id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public String getRecipe() {
        return recipe;
    }
}
