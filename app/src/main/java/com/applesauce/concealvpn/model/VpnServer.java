package com.applesauce.concealvpn.model;

import lombok.Data;

@Data
public class VpnServer {
    private String country, flag, username, ovpn, password;

    VpnServer(String country, String flag, String opvn) {
        this.country = country;
        this.flag = flag;
        this.ovpn = ovpn;
    }
}
