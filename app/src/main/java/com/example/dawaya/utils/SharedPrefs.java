package com.example.dawaya.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    static Context context = App.getAppContext();

    public static SharedPreferences sp;
    static SharedPreferences.Editor  editor;

    //Notice when initializing these strings with "" ((same value for each)) it makes all of them as the value of the last one when reading
    public final static String PREF_NAME = "MyPreference";
    public final static String USER_ID = "USER_ID";
    public final static String FIRST_NAME = "FIRST_NAME";
    public final static String LAST_NAME = "LAST_NAME";
    public final static String EMAIL = "EMAIL";
    public final static String PASSWORD = "PASSWORD";
    public final static String PHONE_NUMBER = "PHONE_NUMBER";
    public final static String GENDER = "GENDER";
    public final static String DATE_OF_BIRTH = "DATE_OF_BIRTH";
    public final static String ADDRESS = "ADDRESS";

    private SharedPrefs(){} // empty Constructor

    // Singleton approach
    // Has to be called once
    public static void init(){
        if (sp == null){
            sp = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
            editor = sp.edit();
        }

    }
    // for retrieving data
    public static String read(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    // for setting data
    public static void write(String key, String value) {
        //SharedPreferences.Editor prefsEditor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void clear(String key){
        //SharedPreferences.Editor prefsEditor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
    public static void clearAll(){
        //SharedPreferences.Editor prefsEditor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
