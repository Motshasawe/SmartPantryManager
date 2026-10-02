package com.smartpantry.manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartpantry.manager.R;
import com.smartpantry.manager.data.PantryDbHelper;
import com.smartpantry.manager.model.PantryItem;

import java.util.List;

public class PantryListActivity extends AppCompatActivity
        implements PantryAdapter.OnItemActionListener {

    public static final String EXTRA_ITEM_ID = "extra_item_id";

    private PantryDbHelper dbHelper;
    private PantryAdapter adapter;
    private TextView textEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = PantryDbHelper.getInstance(this);

        RecyclerView recyclerPantry = findViewById(R.id.recyclerPantry);
        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter(this);
        recyclerPantry.setAdapter(adapter);

        textEmpty = findViewById(R.id.textEmpty);

        android.widget.EditText editSearch = findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(PantryListActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        List<PantryItem> items = dbHelper.getAllPantryItems();
        adapter.setItems(items);
        textEmpty.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void filterList(String query) {
        List<PantryItem> allItems = dbHelper.getAllPantryItems();
        if (query == null || query.trim().isEmpty()) {
            adapter.setItems(allItems);
            textEmpty.setVisibility(allItems.isEmpty() ? View.VISIBLE : View.GONE);
            return;
        }
        List<PantryItem> filtered = new java.util.ArrayList<>();
        String lowerQuery = query.toLowerCase(java.util.Locale.ROOT);
        for (PantryItem item : allItems) {
            if (item.getName().toLowerCase(java.util.Locale.ROOT).contains(lowerQuery)) {
                filtered.add(item);
            }
        }
        adapter.setItems(filtered);
        textEmpty.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onEdit(PantryItem item) {
        Intent intent = new Intent(this, AddEditIngredientActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void onDelete(PantryItem item) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.action_delete)
                .setMessage(getString(R.string.confirm_delete) + " (" + item.getName() + ")")
                .setPositiveButton(R.string.action_delete, (dialog, which) -> {
                    dbHelper.deletePantryItem(item.getId());
                    refreshList();
                })
                .setNegativeButton(R.string.action_cancel, null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_suggested_recipes) {
            startActivity(new Intent(this, SuggestedRecipesActivity.class));
            return true;
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
