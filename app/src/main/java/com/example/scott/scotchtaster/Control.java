package com.example.scott.scotchtaster;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by scott on 2016-01-22.
 */
public final class Control {
    public static final String PREFS_NAME = "DRINK_APP";
    public static final String DRINKS = "drink";

    public Control() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveDrinks(Context context, List<Drink> drinks) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonDrinks = gson.toJson(drinks);

        editor.putString(DRINKS, jsonDrinks);

        editor.commit();
    }

    public void addDrink(Context context, Drink drink) {
        List<Drink> drinks = getDrinks(context);
        if (drinks == null)
            drinks = new ArrayList<Drink>();
        drinks.add(drink);
        saveDrinks(context, drinks);
    }

    public void removeDrink(Context context, Drink drink) {
        ArrayList<Drink> drinks = getDrinks(context);
        if (drinks != null) {
            drinks.remove(drink);
            saveDrinks(context, drinks);
        }
    }

    public ArrayList<Drink> getDrinks(Context context) {
        SharedPreferences settings;
        List<Drink> drinks;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(DRINKS)) {
            String jsonFavorites = settings.getString(DRINKS, null);
            Gson gson = new Gson();
            Drink[] favoriteItems = gson.fromJson(jsonFavorites,
                    Drink[].class);

            drinks = Arrays.asList(favoriteItems);
            drinks = new ArrayList<Drink>(drinks);
        } else
            return null;

        return (ArrayList<Drink>) drinks;
    }
}
