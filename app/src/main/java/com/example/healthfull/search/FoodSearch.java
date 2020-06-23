package com.example.healthfull.search;

import java.util.ArrayList;
import java.util.List;

/**
 * INCOMPLETE
 * FoodSearch is responsible for searching the Firebase food collection asynchronously for food tags
 * matching the query. It includes callbacks for completion with a FoodSearchResults object
 * containing all the returned results
 */
public class FoodSearch {

    /**
     *
     * @param query
     * @return
     */
    public static List<FoodSearchResult> Search(String query) {
        List<FoodSearchResult> results = new ArrayList<>();

        //results.add(new FoodSearchResult("1", "Food 1"));

        return results;
    }
}
