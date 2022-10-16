package com.fitareq.techno71;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import java.util.ArrayList;

public class Utils {
    public static final String URL_KEY = "URL_KEY";
    private static final String MY_PREFS_NAME = "Techno71";
    private static final String SEARCH_KEY = "search";
    private static final String SIZE_KEY = "size";
    private static SharedPreferences.Editor editor = null;
    private static SharedPreferences prefs = null;

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static void saveStringToSharedPref(String value, Context context) {
        initSharedPref(context);
        int size = getIntFromSharedPref();
        ++size;
        String key = SEARCH_KEY + size;
        editor.putString(key, value);
        editor.putInt(SIZE_KEY, size);
        editor.apply();
    }

    private static int getIntFromSharedPref() {
        return prefs.getInt(SIZE_KEY, 0);
    }

    public static ArrayList<String> getRecentSearch(Context context) {
        initSharedPref(context);
        ArrayList<String> searches = new ArrayList<>();
        int size = getIntFromSharedPref();
        if (size > 0) {
            for (int i = 1; i <= size; i++) {
                String key = SEARCH_KEY + i;
                String value = prefs.getString(key, "");
                searches.add(value);
            }
        }

        return searches;

    }


    private static void initSharedPref(Context context) {
        if (editor == null)
            editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        if (prefs == null)
            prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    }
}
