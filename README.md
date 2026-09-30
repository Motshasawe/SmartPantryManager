# Smart Pantry Manager

An Android application (Java) that helps reduce food waste by tracking the ingredients you have at home and suggesting recipes you can cook using **strictly** those ingredients — no shopping trip required.

## Features

- **Pantry management** — add, edit, and delete pantry items (name, quantity, unit, optional expiry date)
- **Pantry list** — RecyclerView bound to a local SQLite database
- **Recipe collection** — 20 recipes pre-loaded on first run
- **Suggested Recipes** — strict-matching logic shows only recipes where every required ingredient is present in sufficient quantity
- **Recipe detail** — full ingredient list and method
- **Settings** — expiring-soon alerts toggle and units preference
- **Empty-state feedback** — clear message when no recipes match

## Technology Stack

- **Language:** Java
- **IDE:** Android Studio
- **Database:** SQLite via `SQLiteOpenHelper` (local, on-device persistence)
- **UI:** AppCompat, Material Components, RecyclerView, ConstraintLayout/LinearLayout
- **Min SDK:** 24 | **Target SDK:** 36

## Database Choice: SQLite

SQLite was chosen because the app is fully offline and self-contained — all data (pantry items, recipes, settings) lives on the device. No network dependency, no cloud costs, and it aligns with the module's persistent-data-storage coverage. The `PantryDbHelper` class manages four tables:

| Table | Purpose |
|---|---|
| `pantry_items` | User's ingredients (name, quantity, unit, expiry) |
| `recipes` | Recipe name and preparation steps |
| `recipe_ingredients` | Ingredients required per recipe (FK to recipes) |
| `settings` | Key-value settings (alerts toggle, units) |

## Project Structure

```
app/src/main/java/com/smartpantry/manager/
├── SmartPantryApp.java          # Application class, initialises DB
├── data/
│   ├── PantryDbHelper.java      # SQLiteOpenHelper, CRUD operations
│   └── SeedData.java            # 20 seeded recipes
├── model/
│   ├── PantryItem.java
│   ├── Recipe.java
│   └── RecipeIngredient.java
├── util/
│   ├── UnitConverter.java       # g/kg/ml/l/oz/cup/tbsp/tsp conversion
│   └── IngredientMatcher.java   # Strict-matching + name normalisation
└── ui/
    ├── PantryListActivity.java
    ├── AddEditIngredientActivity.java
    ├── SuggestedRecipesActivity.java
    ├── RecipeDetailActivity.java
    ├── SettingsActivity.java
    ├── PantryAdapter.java
    └── SuggestedRecipesAdapter.java
```

## Setup & Run

1. Open the project in **Android Studio** (Hedgehog or newer).
2. Let Gradle sync complete.
3. Run on an emulator or physical device (API 24+).

No additional configuration is required — the database and seed data are created automatically on first launch.

## Strict-Matching Rule

A recipe is only suggested when **every** ingredient it requires is present in the pantry in at least the required quantity. The matcher normalises ingredient names (lowercase, singular/plural handling — "tomato" matches "tomatoes") and converts units to a common base (g, ml, piece) before comparing quantities.
