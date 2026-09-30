package com.smartpantry.manager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartpantry.manager.model.PantryItem;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class PantryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PANTRY = "pantry_items";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String TABLE_SETTINGS = "settings";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY = "expiry_date";
    public static final String COL_STEPS = "steps";
    public static final String COL_RECIPE_ID = "recipe_id";
    public static final String COL_SETTING_KEY = "setting_key";
    public static final String COL_SETTING_VALUE = "setting_value";

    private static final String CREATE_PANTRY = "CREATE TABLE " + TABLE_PANTRY + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_QUANTITY + " REAL NOT NULL, "
            + COL_UNIT + " TEXT NOT NULL, "
            + COL_EXPIRY + " TEXT)";

    private static final String CREATE_RECIPES = "CREATE TABLE " + TABLE_RECIPES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_STEPS + " TEXT NOT NULL)";

    private static final String CREATE_RECIPE_INGREDIENTS = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_RECIPE_ID + " INTEGER NOT NULL, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_QUANTITY + " REAL NOT NULL, "
            + COL_UNIT + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + COL_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES + "(" + COL_ID + "))";

    private static final String CREATE_SETTINGS = "CREATE TABLE " + TABLE_SETTINGS + " ("
            + COL_SETTING_KEY + " TEXT PRIMARY KEY, "
            + COL_SETTING_VALUE + " TEXT NOT NULL)";

    private static PantryDbHelper instance;

    public static synchronized PantryDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PantryDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private PantryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PANTRY);
        db.execSQL(CREATE_RECIPES);
        db.execSQL(CREATE_RECIPE_INGREDIENTS);
        db.execSQL(CREATE_SETTINGS);
        SeedData.seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        onCreate(db);
    }

    // ---------------------------------------------------------------------
    // Pantry CRUD
    // ---------------------------------------------------------------------

    public long insertPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_QUANTITY, item.getQuantity());
        values.put(COL_UNIT, item.getUnit());
        values.put(COL_EXPIRY, item.getExpiryDate());
        return db.insert(TABLE_PANTRY, null, values);
    }

    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_QUANTITY, item.getQuantity());
        values.put(COL_UNIT, item.getUnit());
        values.put(COL_EXPIRY, item.getExpiryDate());
        return db.update(TABLE_PANTRY, values, COL_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public int deletePantryItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_PANTRY, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public PantryItem getPantryItem(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, COL_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        PantryItem item = null;
        if (cursor.moveToFirst()) {
            item = cursorToPantryItem(cursor);
        }
        cursor.close();
        return item;
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, null, null, null, null,
                COL_NAME + " COLLATE NOCASE ASC");
        while (cursor.moveToNext()) {
            items.add(cursorToPantryItem(cursor));
        }
        cursor.close();
        return items;
    }

    private PantryItem cursorToPantryItem(Cursor cursor) {
        return new PantryItem(
                cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COL_QUANTITY)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_UNIT)),
                cursor.isNull(cursor.getColumnIndexOrThrow(COL_EXPIRY))
                        ? null : cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPIRY)));
    }

    // ---------------------------------------------------------------------
    // Recipes
    // ---------------------------------------------------------------------

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, null, null, null, null,
                COL_NAME + " COLLATE NOCASE ASC");
        while (cursor.moveToNext()) {
            long recipeId = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
            Recipe recipe = new Recipe(recipeId,
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_STEPS)));
            recipe.setIngredients(getIngredientsForRecipe(recipeId));
            recipes.add(recipe);
        }
        cursor.close();
        return recipes;
    }

    public Recipe getRecipe(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, COL_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        Recipe recipe = null;
        if (cursor.moveToFirst()) {
            recipe = new Recipe(id,
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_STEPS)));
            recipe.setIngredients(getIngredientsForRecipe(id));
        }
        cursor.close();
        return recipe;
    }

    public List<RecipeIngredient> getIngredientsForRecipe(long recipeId) {
        List<RecipeIngredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE_INGREDIENTS, null, COL_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)}, null, null, COL_ID + " ASC");
        while (cursor.moveToNext()) {
            ingredients.add(new RecipeIngredient(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                    recipeId,
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_QUANTITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_UNIT))));
        }
        cursor.close();
        return ingredients;
    }

    // ---------------------------------------------------------------------
    // Settings
    // ---------------------------------------------------------------------

    public String getSetting(String key, String defaultValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_SETTINGS, new String[]{COL_SETTING_VALUE},
                COL_SETTING_KEY + " = ?", new String[]{key}, null, null, null);
        String value = defaultValue;
        if (cursor.moveToFirst()) {
            value = cursor.getString(0);
        }
        cursor.close();
        return value;
    }

    public void putSetting(String key, String value) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SETTING_KEY, key);
        values.put(COL_SETTING_VALUE, value);
        db.insertWithOnConflict(TABLE_SETTINGS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
