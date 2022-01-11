package com.applesauce.concealvpn.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class VpnServer {
    private String country, flag, username, ovpn, password;

    VpnServer(String country, String flag, String opvn) {
        this.country = country;
        this.flag = flag;
        this.ovpn = ovpn;
    }
}
