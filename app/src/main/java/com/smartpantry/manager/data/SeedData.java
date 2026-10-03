package com.smartpantry.manager.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public final class SeedData {

    private SeedData() {
    }

    public static void seed(SQLiteDatabase db) {
        insertRecipe(db, "Classic Tomato Pasta",
                "1. Boil water and cook pasta until al dente.\n"
                        + "2. Heat olive oil in a pan, saute garlic until fragrant.\n"
                        + "3. Add chopped tomatoes, salt and pepper; simmer 10 minutes.\n"
                        + "4. Toss pasta into the sauce and serve with grated cheese.",
                new String[][]{
                        {"Pasta", "200", "g"},
                        {"Tomato", "4", "piece"},
                        {"Garlic", "2", "clove"},
                        {"Olive oil", "2", "tbsp"},
                        {"Salt", "1", "tsp"},
                        {"Cheese", "30", "g"}
                });

        insertRecipe(db, "Vegetable Stir Fry",
                "1. Chop all vegetables into bite-sized pieces.\n"
                        + "2. Heat oil in a wok over high heat.\n"
                        + "3. Add onion and garlic, stir fry 1 minute.\n"
                        + "4. Add carrot, bell pepper and broccoli; cook 5 minutes.\n"
                        + "5. Season with salt and serve over rice.",
                new String[][]{
                        {"Rice", "150", "g"},
                        {"Carrot", "1", "piece"},
                        {"Bell pepper", "1", "piece"},
                        {"Broccoli", "100", "g"},
                        {"Onion", "1", "piece"},
                        {"Garlic", "2", "clove"},
                        {"Olive oil", "1", "tbsp"}
                });

        insertRecipe(db, "Cheese Omelette",
                "1. Whisk eggs with a pinch of salt.\n"
                        + "2. Heat butter in a non-stick pan.\n"
                        + "3. Pour in eggs, cook until edges set.\n"
                        + "4. Sprinkle cheese on one half, fold and serve.",
                new String[][]{
                        {"Egg", "3", "piece"},
                        {"Cheese", "40", "g"},
                        {"Butter", "10", "g"},
                        {"Salt", "1", "pinch"}
                });

        insertRecipe(db, "Garlic Butter Rice",
                "1. Rinse rice and cook with water.\n"
                        + "2. Melt butter in a pan, saute minced garlic.\n"
                        + "3. Mix garlic butter through the rice.\n"
                        + "4. Season with salt and serve.",
                new String[][]{
                        {"Rice", "200", "g"},
                        {"Garlic", "3", "clove"},
                        {"Butter", "20", "g"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Tomato Soup",
                "1. Saute onion and garlic in olive oil.\n"
                        + "2. Add chopped tomatoes and simmer 15 minutes.\n"
                        + "3. Blend until smooth.\n"
                        + "4. Season with salt and pepper; serve with bread.",
                new String[][]{
                        {"Tomato", "6", "piece"},
                        {"Onion", "1", "piece"},
                        {"Garlic", "2", "clove"},
                        {"Olive oil", "1", "tbsp"},
                        {"Bread", "2", "slice"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Mushroom Pasta",
                "1. Cook pasta until al dente.\n"
                        + "2. Saute sliced mushrooms in olive oil.\n"
                        + "3. Add garlic and cook 1 more minute.\n"
                        + "4. Toss pasta with mushrooms; top with cheese.",
                new String[][]{
                        {"Pasta", "200", "g"},
                        {"Mushroom", "150", "g"},
                        {"Garlic", "2", "clove"},
                        {"Olive oil", "2", "tbsp"},
                        {"Cheese", "30", "g"}
                });

        insertRecipe(db, "Scrambled Eggs on Toast",
                "1. Whisk eggs with salt.\n"
                        + "2. Melt butter in a pan over low heat.\n"
                        + "3. Add eggs and stir gently until just set.\n"
                        + "4. Toast bread and serve eggs on top.",
                new String[][]{
                        {"Egg", "2", "piece"},
                        {"Bread", "2", "slice"},
                        {"Butter", "10", "g"},
                        {"Salt", "1", "pinch"}
                });

        insertRecipe(db, "Roasted Vegetables",
                "1. Preheat oven to 200 C.\n"
                        + "2. Chop carrot, bell pepper and broccoli.\n"
                        + "3. Toss vegetables with olive oil and salt.\n"
                        + "4. Roast 25 minutes until tender.",
                new String[][]{
                        {"Carrot", "2", "piece"},
                        {"Bell pepper", "1", "piece"},
                        {"Broccoli", "150", "g"},
                        {"Olive oil", "2", "tbsp"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Banana Pancakes",
                "1. Mash banana in a bowl.\n"
                        + "2. Whisk in egg and a pinch of salt.\n"
                        + "3. Cook spoonfuls of batter in buttered pan.\n"
                        + "4. Flip when bubbles form; serve warm.",
                new String[][]{
                        {"Banana", "1", "piece"},
                        {"Egg", "1", "piece"},
                        {"Butter", "10", "g"},
                        {"Salt", "1", "pinch"}
                });

        insertRecipe(db, "Chicken and Rice",
                "1. Season chicken with salt.\n"
                        + "2. Cook rice according to packet.\n"
                        + "3. Pan-fry chicken in olive oil until cooked through.\n"
                        + "4. Serve chicken over rice with vegetables.",
                new String[][]{
                        {"Chicken breast", "200", "g"},
                        {"Rice", "150", "g"},
                        {"Carrot", "1", "piece"},
                        {"Olive oil", "1", "tbsp"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Caprese Salad",
                "1. Slice tomatoes and arrange on a plate.\n"
                        + "2. Top with cheese slices.\n"
                        + "3. Drizzle with olive oil.\n"
                        + "4. Season with salt and serve.",
                new String[][]{
                        {"Tomato", "3", "piece"},
                        {"Cheese", "80", "g"},
                        {"Olive oil", "1", "tbsp"},
                        {"Salt", "1", "pinch"}
                });

        insertRecipe(db, "Broccoli Cheese Soup",
                "1. Saute onion in butter.\n"
                        + "2. Add broccoli and simmer 10 minutes.\n"
                        + "3. Stir in cheese until melted.\n"
                        + "4. Season with salt and serve with bread.",
                new String[][]{
                        {"Broccoli", "200", "g"},
                        {"Onion", "1", "piece"},
                        {"Cheese", "60", "g"},
                        {"Butter", "15", "g"},
                        {"Bread", "2", "slice"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Fried Rice",
                "1. Cook rice and let it cool.\n"
                        + "2. Scramble egg in a hot oiled wok.\n"
                        + "3. Add rice, carrot and onion; stir fry 5 minutes.\n"
                        + "4. Season with salt and serve.",
                new String[][]{
                        {"Rice", "200", "g"},
                        {"Egg", "1", "piece"},
                        {"Carrot", "1", "piece"},
                        {"Onion", "1", "piece"},
                        {"Olive oil", "2", "tbsp"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Garlic Bread",
                "1. Mix softened butter with minced garlic and salt.\n"
                        + "2. Spread onto bread slices.\n"
                        + "3. Bake at 180 C for 10 minutes.\n"
                        + "4. Serve warm.",
                new String[][]{
                        {"Bread", "4", "slice"},
                        {"Garlic", "3", "clove"},
                        {"Butter", "30", "g"},
                        {"Salt", "1", "pinch"}
                });

        insertRecipe(db, "Egg Fried Rice",
                        "1. Scramble egg in a wok with oil.\n"
                        + "2. Add cold rice and stir fry 4 minutes.\n"
                        + "3. Add diced carrot and cook 2 more minutes.\n"
                        + "4. Season with salt and serve.",
                new String[][]{
                        {"Rice", "200", "g"},
                        {"Egg", "2", "piece"},
                        {"Carrot", "1", "piece"},
                        {"Olive oil", "2", "tbsp"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Mushroom Omelette",
                "1. Saute sliced mushrooms in butter.\n"
                        + "2. Whisk eggs with salt.\n"
                        + "3. Pour eggs over mushrooms; cook until set.\n"
                        + "4. Fold, top with cheese and serve.",
                new String[][]{
                        {"Egg", "3", "piece"},
                        {"Mushroom", "100", "g"},
                        {"Butter", "10", "g"},
                        {"Cheese", "30", "g"},
                        {"Salt", "1", "pinch"}
                });

        insertRecipe(db, "Tomato Omelette",
                "1. Saute chopped tomato in olive oil.\n"
                        + "2. Whisk eggs with salt.\n"
                        + "3. Pour eggs over tomato; cook until set.\n"
                        + "4. Fold and serve with bread.",
                new String[][]{
                        {"Egg", "3", "piece"},
                        {"Tomato", "2", "piece"},
                        {"Olive oil", "1", "tbsp"},
                        {"Bread", "2", "slice"},
                        {"Salt", "1", "pinch"}
                });

        insertRecipe(db, "Cheese Toastie",
                "1. Butter the bread slices.\n"
                        + "2. Sandwich cheese between slices.\n"
                        + "3. Toast in a pan until golden on both sides.\n"
                        + "4. Serve warm.",
                new String[][]{
                        {"Bread", "2", "slice"},
                        {"Cheese", "50", "g"},
                        {"Butter", "10", "g"}
                });

        insertRecipe(db, "Carrot Soup",
                "1. Saute onion in olive oil.\n"
                        + "2. Add chopped carrot and simmer 15 minutes.\n"
                        + "3. Blend until smooth.\n"
                        + "4. Season with salt and serve with bread.",
                new String[][]{
                        {"Carrot", "4", "piece"},
                        {"Onion", "1", "piece"},
                        {"Olive oil", "1", "tbsp"},
                        {"Bread", "2", "slice"},
                        {"Salt", "1", "tsp"}
                });

        insertRecipe(db, "Broccoli Pasta",
                "1. Cook pasta until al dente.\n"
                        + "2. Saute broccoli and garlic in olive oil.\n"
                        + "3. Toss pasta with broccoli.\n"
                        + "4. Top with cheese and serve.",
                new String[][]{
                        {"Pasta", "200", "g"},
                        {"Broccoli", "150", "g"},
                        {"Garlic", "2", "clove"},
                        {"Olive oil", "2", "tbsp"},
                        {"Cheese", "30", "g"}
                });

        seedPantryItems(db);
    }

    private static void seedPantryItems(SQLiteDatabase db) {
        insertPantryItem(db, "Tomato", 6, "piece", "2026-10-10");
        insertPantryItem(db, "Pasta", 500, "g", null);
        insertPantryItem(db, "Bread", 4, "slice", "2026-10-05");
        insertPantryItem(db, "Cheese", 100, "g", "2026-10-09");
        insertPantryItem(db, "Butter", 50, "g", "2026-10-08");
        insertPantryItem(db, "Olive oil", 3, "tbsp", null);
        insertPantryItem(db, "Salt", 5, "tsp", null);
    }

    private static void insertPantryItem(SQLiteDatabase db, String name, double quantity, String unit, String expiryDate) {
        ContentValues values = new ContentValues();
        values.put(PantryDbHelper.COL_NAME, name);
        values.put(PantryDbHelper.COL_QUANTITY, quantity);
        values.put(PantryDbHelper.COL_UNIT, unit);
        values.put(PantryDbHelper.COL_EXPIRY, expiryDate);
        db.insert(PantryDbHelper.TABLE_PANTRY, null, values);
    }

    private static void insertRecipe(SQLiteDatabase db, String name, String steps, String[][] ingredients) {
        ContentValues recipeValues = new ContentValues();
        recipeValues.put(PantryDbHelper.COL_NAME, name);
        recipeValues.put(PantryDbHelper.COL_STEPS, steps);
        long recipeId = db.insert(PantryDbHelper.TABLE_RECIPES, null, recipeValues);

        for (String[] ingredient : ingredients) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put(PantryDbHelper.COL_RECIPE_ID, recipeId);
            ingredientValues.put(PantryDbHelper.COL_NAME, ingredient[0]);
            ingredientValues.put(PantryDbHelper.COL_QUANTITY, Double.parseDouble(ingredient[1]));
            ingredientValues.put(PantryDbHelper.COL_UNIT, ingredient[2]);
            db.insert(PantryDbHelper.TABLE_RECIPE_INGREDIENTS, null, ingredientValues);
        }
    }
}
