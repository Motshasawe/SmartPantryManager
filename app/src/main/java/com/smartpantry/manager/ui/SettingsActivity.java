package com.smartpantry.manager.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartpantry.manager.R;
import com.smartpantry.manager.data.PantryDbHelper;

public class SettingsActivity extends AppCompatActivity {

    private static final String KEY_EXPIRING_ALERTS = "expiring_alerts";
    private static final String KEY_UNITS = "units";
    private static final String UNITS_METRIC = "metric";
    private static final String UNITS_IMPERIAL = "imperial";

    private PantryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = PantryDbHelper.getInstance(this);

        SwitchMaterial switchExpiringAlerts = findViewById(R.id.switchExpiringAlerts);
        boolean alertsEnabled = Boolean.parseBoolean(
                dbHelper.getSetting(KEY_EXPIRING_ALERTS, "true"));
        switchExpiringAlerts.setChecked(alertsEnabled);
        switchExpiringAlerts.setOnCheckedChangeListener((buttonView, isChecked) ->
                dbHelper.putSetting(KEY_EXPIRING_ALERTS, String.valueOf(isChecked)));

        String units = dbHelper.getSetting(KEY_UNITS, UNITS_METRIC);
        if (UNITS_IMPERIAL.equals(units)) {
            findViewById(R.id.radioImperial).setSelected(true);
        } else {
            findViewById(R.id.radioMetric).setSelected(true);
        }

        findViewById(R.id.radioMetric).setOnClickListener(v ->
                dbHelper.putSetting(KEY_UNITS, UNITS_METRIC));
        findViewById(R.id.radioImperial).setOnClickListener(v ->
                dbHelper.putSetting(KEY_UNITS, UNITS_IMPERIAL));
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
