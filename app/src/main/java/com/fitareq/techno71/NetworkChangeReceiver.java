package com.fitareq.techno71;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NoInternetListener noInternetListener = (NoInternetListener) context;
        noInternetListener.noInternet(Utils.isNetworkAvailable(context));
    }
}
