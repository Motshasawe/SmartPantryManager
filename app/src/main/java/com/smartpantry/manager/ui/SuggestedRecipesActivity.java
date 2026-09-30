package com.smartpantry.manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartpantry.manager.R;
import com.smartpantry.manager.data.PantryDbHelper;
import com.smartpantry.manager.model.PantryItem;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.util.IngredientMatcher;

import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity
        implements SuggestedRecipesAdapter.OnRecipeClickListener {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    private PantryDbHelper dbHelper;
    private SuggestedRecipesAdapter adapter;
    private TextView textEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = PantryDbHelper.getInstance(this);

        RecyclerView recyclerRecipes = findViewById(R.id.recyclerRecipes);
        recyclerRecipes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuggestedRecipesAdapter(this);
        recyclerRecipes.setAdapter(adapter);

        textEmpty = findViewById(R.id.textEmpty);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSuggestions();
    }

    private void refreshSuggestions() {
        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();
        List<Recipe> allRecipes = dbHelper.getAllRecipes();
        List<Recipe> matches = IngredientMatcher.getStrictMatches(allRecipes, pantryItems);
        adapter.setRecipes(matches);
        textEmpty.setVisibility(matches.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipe.getId());
        startActivity(intent);
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
