package com.smartpantry.manager.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.smartpantry.manager.R;
import com.smartpantry.manager.data.PantryDbHelper;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeIngredient;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        long recipeId = getIntent().getLongExtra(SuggestedRecipesActivity.EXTRA_RECIPE_ID, -1);
        if (recipeId < 0) {
            finish();
            return;
        }

        PantryDbHelper dbHelper = PantryDbHelper.getInstance(this);
        Recipe recipe = dbHelper.getRecipe(recipeId);
        if (recipe == null) {
            finish();
            return;
        }

        TextView textRecipeName = findViewById(R.id.textRecipeName);
        TextView textIngredients = findViewById(R.id.textIngredients);
        TextView textSteps = findViewById(R.id.textSteps);

        textRecipeName.setText(recipe.getName());

        StringBuilder ingredientText = new StringBuilder();
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            ingredientText.append("• ")
                    .append(formatQuantity(ingredient.getQuantity()))
                    .append(" ").append(ingredient.getUnit())
                    .append(" ").append(ingredient.getName())
                    .append("\n");
        }
        textIngredients.setText(ingredientText.toString().trim());
        textSteps.setText(recipe.getSteps());
    }

    private String formatQuantity(double quantity) {
        if (quantity == Math.floor(quantity)) {
            return String.valueOf((int) quantity);
        }
        return String.valueOf(quantity);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
