package com.applesauce.concealvpn.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class VpnServer {
    String country, flag, username, password, ovpn;
}
