package com.smartpantry.manager;

import com.smartpantry.manager.model.PantryItem;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeIngredient;
import com.smartpantry.manager.util.IngredientMatcher;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IngredientMatcherTest {

    private Recipe recipeWith(String... ingredientNames) {
        Recipe recipe = new Recipe(1, "Test Recipe", "steps");
        List<RecipeIngredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientNames.length; i++) {
            ingredients.add(new RecipeIngredient(i, 1, ingredientNames[i], 1, "piece"));
        }
        recipe.setIngredients(ingredients);
        return recipe;
    }

    private PantryItem pantryItem(String name, double qty, String unit) {
        return new PantryItem(1, name, qty, unit, null);
    }

    @Test
    public void exactMatch_allIngredientsPresent_returnsTrue() {
        Recipe recipe = recipeWith("Tomato", "Onion", "Garlic");
        List<PantryItem> pantry = Arrays.asList(
                pantryItem("Tomato", 3, "piece"),
                pantryItem("Onion", 2, "piece"),
                pantryItem("Garlic", 5, "clove"));
        assertTrue(IngredientMatcher.canMakeRecipe(recipe, pantry));
    }

    @Test
    public void missingOneIngredient_returnsFalse() {
        Recipe recipe = recipeWith("Tomato", "Onion", "Garlic");
        List<PantryItem> pantry = Arrays.asList(
                pantryItem("Tomato", 3, "piece"),
                pantryItem("Onion", 2, "piece"));
        assertFalse(IngredientMatcher.canMakeRecipe(recipe, pantry));
    }

    @Test
    public void singularPluralMatch_tomatoMatchesTomatoes() {
        Recipe recipe = recipeWith("Tomato");
        List<PantryItem> pantry = Collections.singletonList(
                pantryItem("tomatoes", 3, "piece"));
        assertTrue(IngredientMatcher.canMakeRecipe(recipe, pantry));
    }

    @Test
    public void unitConversion_kgMatchesGrams() {
        Recipe recipe = recipeWith("Flour");
        recipe.getIngredients().get(0).setQuantity(500);
        recipe.getIngredients().get(0).setUnit("g");
        List<PantryItem> pantry = Collections.singletonList(
                pantryItem("flour", 1, "kg"));
        assertTrue(IngredientMatcher.canMakeRecipe(recipe, pantry));
    }

    @Test
    public void insufficientQuantity_returnsFalse() {
        Recipe recipe = recipeWith("Egg");
        recipe.getIngredients().get(0).setQuantity(3);
        recipe.getIngredients().get(0).setUnit("piece");
        List<PantryItem> pantry = Collections.singletonList(
                pantryItem("egg", 2, "piece"));
        assertFalse(IngredientMatcher.canMakeRecipe(recipe, pantry));
    }

    @Test
    public void emptyPantry_returnsFalse() {
        Recipe recipe = recipeWith("Tomato");
        assertFalse(IngredientMatcher.canMakeRecipe(recipe, Collections.emptyList()));
    }

    @Test
    public void emptyRecipe_returnsTrue() {
        Recipe recipe = new Recipe(1, "Empty", "steps");
        recipe.setIngredients(Collections.emptyList());
        assertTrue(IngredientMatcher.canMakeRecipe(recipe, Collections.emptyList()));
    }

    @Test
    public void almostThere_missingExactlyOne_returnsTrue() {
        Recipe recipe = recipeWith("Tomato", "Onion", "Garlic");
        List<PantryItem> pantry = Arrays.asList(
                pantryItem("Tomato", 3, "piece"),
                pantryItem("Onion", 2, "piece"));
        List<Recipe> recipes = Collections.singletonList(recipe);
        List<Recipe> almost = IngredientMatcher.getAlmostThere(recipes, pantry);
        assertEquals(1, almost.size());
    }

    @Test
    public void strictMatches_excludesAlmostThere() {
        Recipe recipe = recipeWith("Tomato", "Onion", "Garlic");
        List<PantryItem> pantry = Arrays.asList(
                pantryItem("Tomato", 3, "piece"),
                pantryItem("Onion", 2, "piece"));
        List<Recipe> recipes = Collections.singletonList(recipe);
        List<Recipe> strict = IngredientMatcher.getStrictMatches(recipes, pantry);
        assertTrue(strict.isEmpty());
    }
}
