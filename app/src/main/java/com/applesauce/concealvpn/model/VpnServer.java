package com.applesauce.concealvpn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class VpnServer {
    String country, flag, username, ovpn, password;

    VpnServer(String country, String flag, String opvn) {
        this.country = country;
        this.flag = flag;
        this.ovpn = ovpn;
    }
}
