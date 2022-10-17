package com.fitareq.techno71;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.Map;

public class Utils {
    public static final String URL_KEY = "URL_KEY";
    private static final String MY_PREFS_NAME = "Techno71";
    private static SharedPreferences.Editor editor = null;
    private static SharedPreferences prefs = null;

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static void saveStringToSharedPref(String value, Context context) {
        initSharedPref(context);
        editor.putString(value, value);
        editor.apply();
    }

    public static void removeStringFromSharedPref(String key, Context context){
        initSharedPref(context);
        editor.remove(key).apply();
    }

    public static ArrayList<String> getRecentSearch(Context context) {
        initSharedPref(context);
        ArrayList<String> searches = new ArrayList<>();

        Map<String, ?> value = prefs.getAll();
        for (Map.Entry<String, ?> entry: value.entrySet()){
            searches.add(entry.getValue().toString());
        }
        /*int size = getIntFromSharedPref();
        if (size > 0) {
            for (int i = 1; i <= size; i++) {
                String key = SEARCH_KEY + i;
                String value = prefs.getString(key, "");
                searches.add(value);
            }
        }*/

        return searches;

    }


    private static void initSharedPref(Context context) {
        if (editor == null)
            editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        if (prefs == null)
            prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    }
}
