package com.smartpantry.manager.util;

import com.smartpantry.manager.model.PantryItem;
import com.smartpantry.manager.model.Recipe;
import com.smartpantry.manager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class IngredientMatcher {

    private IngredientMatcher() {
    }

    public static String normalizeName(String name) {
        if (name == null) {
            return "";
        }
        String normalized = name.trim().toLowerCase(Locale.ROOT);
        normalized = normalized.replaceAll("\\s+", " ");
        return singularize(normalized);
    }

    private static String singularize(String word) {
        if (word.endsWith("ies") && word.length() > 3) {
            return word.substring(0, word.length() - 3) + "y";
        }
        if (word.endsWith("oes") && word.length() > 3) {
            return word.substring(0, word.length() - 2);
        }
        if (word.endsWith("es") && word.length() > 3) {
            String withoutEs = word.substring(0, word.length() - 2);
            if (withoutEs.endsWith("s") || withoutEs.endsWith("sh") || withoutEs.endsWith("ch")) {
                return withoutEs;
            }
        }
        if (word.endsWith("s") && !word.endsWith("ss") && word.length() > 1) {
            return word.substring(0, word.length() - 1);
        }
        return word;
    }

    private static Map<String, Double> buildPantryIndex(List<PantryItem> pantryItems) {
        Map<String, Double> index = new HashMap<>();
        for (PantryItem item : pantryItems) {
            String key = normalizeName(item.getName());
            double baseQty = UnitConverter.convertToBase(item.getQuantity(), item.getUnit());
            index.merge(key, baseQty, Double::sum);
        }
        return index;
    }

    public static boolean canMakeRecipe(Recipe recipe, List<PantryItem> pantryItems) {
        return countMissingIngredients(recipe, pantryItems) == 0;
    }

    public static int countMissingIngredients(Recipe recipe, List<PantryItem> pantryItems) {
        if (recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) {
            return 0;
        }
        Map<String, Double> pantryIndex = buildPantryIndex(pantryItems);
        int missing = 0;
        for (RecipeIngredient required : recipe.getIngredients()) {
            if (!hasSufficientQuantity(required, pantryIndex)) {
                missing++;
            }
        }
        return missing;
    }

    private static boolean hasSufficientQuantity(RecipeIngredient required,
                                                 Map<String, Double> pantryIndex) {
        String key = normalizeName(required.getName());
        Double available = pantryIndex.get(key);
        if (available == null) {
            return false;
        }
        double requiredBase = UnitConverter.convertToBase(required.getQuantity(), required.getUnit());
        return available + 1e-6 >= requiredBase;
    }

    public static List<Recipe> getStrictMatches(List<Recipe> recipes, List<PantryItem> pantryItems) {
        List<Recipe> matches = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (canMakeRecipe(recipe, pantryItems)) {
                matches.add(recipe);
            }
        }
        return matches;
    }

    public static List<Recipe> getAlmostThere(List<Recipe> recipes, List<PantryItem> pantryItems) {
        List<Recipe> almost = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (countMissingIngredients(recipe, pantryItems) == 1) {
                almost.add(recipe);
            }
        }
        return almost;
    }
}
