package com.smartpantry.manager.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smartpantry.manager.R;
import com.smartpantry.manager.data.PantryDbHelper;
import com.smartpantry.manager.model.PantryItem;

import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    private static final String[] UNITS = {
            "g", "kg", "ml", "l", "oz", "lb", "cup", "tbsp", "tsp",
            "piece", "clove", "slice", "pinch", "can", "bunch"
    };

    private PantryDbHelper dbHelper;
    private TextInputLayout layoutName;
    private TextInputLayout layoutQuantity;
    private TextInputLayout layoutExpiry;
    private TextInputEditText editName;
    private TextInputEditText editQuantity;
    private TextInputEditText editExpiry;
    private Spinner spinnerUnit;

    private long editingItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = PantryDbHelper.getInstance(this);

        layoutName = findViewById(R.id.layoutName);
        layoutQuantity = findViewById(R.id.layoutQuantity);
        layoutExpiry = findViewById(R.id.layoutExpiry);
        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editExpiry = findViewById(R.id.editExpiry);
        spinnerUnit = findViewById(R.id.spinnerUnit);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, UNITS);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        MaterialButton buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> attemptSave());

        if (getIntent().hasExtra(PantryListActivity.EXTRA_ITEM_ID)) {
            editingItemId = getIntent().getLongExtra(PantryListActivity.EXTRA_ITEM_ID, -1);
            setTitle(R.string.title_edit_ingredient);
            toolbar.setTitle(R.string.title_edit_ingredient);
            loadItem(editingItemId);
        } else {
            setTitle(R.string.title_add_ingredient);
        }
    }

    private void loadItem(long id) {
        PantryItem item = dbHelper.getPantryItem(id);
        if (item == null) {
            finish();
            return;
        }
        editName.setText(item.getName());
        editQuantity.setText(String.valueOf(item.getQuantity()));
        if (item.getExpiryDate() != null) {
            editExpiry.setText(item.getExpiryDate());
        }
        for (int i = 0; i < UNITS.length; i++) {
            if (UNITS[i].equals(item.getUnit())) {
                spinnerUnit.setSelection(i);
                break;
            }
        }
    }

    private void attemptSave() {
        layoutName.setError(null);
        layoutQuantity.setError(null);
        layoutExpiry.setError(null);

        String name = editName.getText() == null ? "" : editName.getText().toString().trim();
        String quantityStr = editQuantity.getText() == null
                ? "" : editQuantity.getText().toString().trim();
        String expiry = editExpiry.getText() == null
                ? "" : editExpiry.getText().toString().trim();
        String unit = spinnerUnit.getSelectedItem().toString();

        boolean valid = true;

        if (TextUtils.isEmpty(name)) {
            layoutName.setError(getString(R.string.error_name_empty));
            valid = false;
        }

        double quantity = 0;
        try {
            quantity = Double.parseDouble(quantityStr);
            if (quantity <= 0) {
                layoutQuantity.setError(getString(R.string.error_quantity_invalid));
                valid = false;
            }
        } catch (NumberFormatException e) {
            layoutQuantity.setError(getString(R.string.error_quantity_invalid));
            valid = false;
        }

        if (!TextUtils.isEmpty(expiry) && !isValidDate(expiry)) {
            layoutExpiry.setError(getString(R.string.error_expiry_invalid));
            valid = false;
        }

        if (!valid) {
            return;
        }

        PantryItem item = new PantryItem();
        item.setName(name);
        item.setQuantity(quantity);
        item.setUnit(unit);
        item.setExpiryDate(TextUtils.isEmpty(expiry) ? null : expiry);

        if (editingItemId > 0) {
            item.setId(editingItemId);
            dbHelper.updatePantryItem(item);
        } else {
            dbHelper.insertPantryItem(item);
        }
        finish();
    }

    private boolean isValidDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        try {
            java.time.LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
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
