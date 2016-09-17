package com.vinaymaneti.coderschooltodolistapp.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vinaymaneti on 9/17/16.
 */
public class Prefs {
    public static final String PRE_LOAD = "pre_load";
    public static final String PREFS_NAME = "prefs";
    private static Prefs instance;
    private final SharedPreferences mSharedPreferences;

    public Prefs(Context context) {
        mSharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs with(Context context) {

        if (instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public void setPreLoad(boolean totalTime) {
        mSharedPreferences.edit().putBoolean(PRE_LOAD, totalTime).apply();
    }

    public boolean getPreLoad() {
        return mSharedPreferences.getBoolean(PRE_LOAD, false);
    }
}
