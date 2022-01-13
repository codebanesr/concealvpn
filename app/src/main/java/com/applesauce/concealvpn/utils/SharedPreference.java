package com.applesauce.concealvpn.utils;

import static com.applesauce.concealvpn.utils.Helper.getResourceAsUrl;

import android.content.Context;
import android.content.SharedPreferences;

import com.applesauce.concealvpn.R;
import com.applesauce.concealvpn.model.VpnServer;


public class SharedPreference {
    //    sp -> SharedPreference
    public static final String app_sp_name = "concealvpn_preference";

    private SharedPreferences sp;
    private SharedPreferences.Editor sp_editor;
    private Context context;

    public SharedPreference(Context context) {
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



    public VpnServer getServer() {
        // if a vpn server is not currently setup, which would be the case during initialization use
        // japan's vpn server
        VpnServer server = new VpnServer(
                sp.getString(VpnConstants.COUNTRY, "Japan"),
                sp.getString(VpnConstants.FLAG, getResourceAsUrl(R.drawable.japan_flag)),
                sp.getString(VpnConstants.SERVER, "japan.ovpn"),
                sp.getString(VpnConstants.USER, "vpn"),
                sp.getString(VpnConstants.PASSWORD, "vpn")
        );

        return server;
    }
}
