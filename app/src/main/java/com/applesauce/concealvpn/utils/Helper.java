package com.applesauce.concealvpn.utils;

import android.net.Uri;

public class Helper {

    public static String getResourceAsUrl(int res) {
        return String.valueOf(Uri.parse("android.resource://com.applesauce.concealvpn/" + res));
    }
}
