package com.applesauce.concealvpn.utils;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;

public class InternetChecker {
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        return netinfo!=null && netinfo.isConnectedOrConnecting();
    }
}
