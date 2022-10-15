package com.fitareq.techno71;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {
    public static final String URL_KEY = "URL_KEY";

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static void saveToSharedPref(String key, String value, Context context){

    }
}
