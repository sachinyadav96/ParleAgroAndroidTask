package com.android.parleagro.Util;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesServices {

    private static final String PREF_FILE = "parle_agro";
    private static final String PREF_InsterValue = "insertValue";
    private static final String PREF_FirstTime = "firsttime";
    private static final String PREF_Lock = "lock";

    public  static void saveToPref(Context context, String str) {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("code", str);
        editor.apply();
    }

    public  static String getCode(Context context) {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE,
                Context.MODE_PRIVATE);
        final String defaultValue = "";
        return sharedPref.getString("code", defaultValue);
    }

    public  static void SetISInsertValue(Context context, String str) {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_InsterValue,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("value", str);
        editor.apply();
    }

    public  static String getISInsertValue(Context context) {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_InsterValue,
                Context.MODE_PRIVATE);
        final String defaultValue = "";
        return sharedPref.getString("value", defaultValue);
    }

    public  static void SetISFirstTime(Context context, String str) {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_FirstTime,
                Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("value", str);
        editor.apply();
    }

    public   static String getISFirstTime(Context context) {
        final SharedPreferences sharedPref = context.getSharedPreferences(PREF_FirstTime,
                Context.MODE_PRIVATE);
        final String defaultValue = "";
        return sharedPref.getString("value", defaultValue);
    }


}
