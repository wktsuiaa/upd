package com.example.chorus.udp_test.Service.helper;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import static android.content.Context.WIFI_SERVICE;

public class UHelper {

    public static void get_IP(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ipA = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Log.i("IP",ipA.toString());

    }
}
