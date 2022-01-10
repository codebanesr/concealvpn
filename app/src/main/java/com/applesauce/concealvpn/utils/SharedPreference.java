package com.applesauce.concealvpn.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.applesauce.concealvpn.model.VpnServer;


public class SharedPreference {
    //    sp -> SharedPreference
    public static final String app_sp_name = "concealvpn_preference";

    private SharedPreferences sp;
    private SharedPreferences.Editor sp_editor;
    private Context context;

    SharedPreference(Context context) {
        sp = context.getSharedPreferences(app_sp_name, Context.MODE_PRIVATE);
        sp_editor = sp.edit();
        this.context = context;
    }



    private void loadServer(VpnServer server) {
        sp_editor.putString(VpnConstants.COUNTRY, server.getCountry());
        sp_editor.putString(VpnConstants.FLAG, server.getFlag());
        sp_editor.putString(VpnConstants.SERVER, server.getOvpn());
        sp_editor.putString(VpnConstants.USER, server.getUsername());
        sp_editor.putString(VpnConstants.PASSWORD, server.getPassword());
        sp_editor.commit();
    }
}
