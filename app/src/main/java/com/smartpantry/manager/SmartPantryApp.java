package com.smartpantry.manager;

import android.app.Application;

import com.smartpantry.manager.data.PantryDbHelper;

public class SmartPantryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PantryDbHelper.getInstance(this).getWritableDatabase();
    }
}
