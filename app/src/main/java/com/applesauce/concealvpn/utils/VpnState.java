package com.applesauce.concealvpn.utils;

import lombok.Data;
@Data
public class VpnState {
    public static final String Connected = "Connected";
    public static final String Disconnected = "Disconnected";
    public static final String WAIT = "WAIT";
    public static final String AUTH = "AUTH";
    public static final String RECONNECTING = "RECONNECTING";
    public static final String NONETWORK = "NONETWORK";
}