package com.ampinityit.Brahmaent;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Created by Admin on 14-04-17.
 */

public class SharedPreferencesUtils {

    public static void addStringToSharedPreferences(String Key, String Value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Key,Value);
        editor.commit();
        editor.apply();

    }

    public static void addIntegerToSharedPreferences(String Key, int Value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Key,Value);
        editor.commit();
        editor.apply();

    }

    public static void addBooleanToSharedPreferences(String Key, boolean Value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Key,Value);
        editor.commit();
        editor.apply();

    }

    public static void addFloatToSharedPreferences(String Key, float Value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(Key,Value);
        editor.commit();
        editor.apply();
    }

    public static void addLongToSharedPreferences(String Key, long Value, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(Key,Value);
        editor.commit();
        editor.apply();
    }

    public static String getStringFromSharedPreference(String Key, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = sharedPreferences.getString(Key,"");
        return value;
    }
    public static Boolean getBooleanFromSharedPreference(String Key, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean value = sharedPreferences.getBoolean(Key,false);
        return value;
    }
    public static Integer getIntegerFromSharedPreference(String Key, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Integer value = sharedPreferences.getInt(Key,0);
        return value;
    }


}