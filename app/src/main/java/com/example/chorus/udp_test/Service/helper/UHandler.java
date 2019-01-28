package com.example.chorus.udp_test.Service.helper;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;

import com.example.chorus.udp_test.MainActivity;

import static android.content.Context.WIFI_SERVICE;

public class UHandler extends Handler {

    /**
    public static void showResult(String output){
        Handler UHandler=MainActivity.UHandler;
        Message msg=Message.obtain();
        msg.what=1;
        msg.obj=output;
        UHandler.sendMessage(msg);
        int port=MainActivity.SERVERPORT;
    }
    **/




    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        //result.append((String)msg.obj+"\n");
        Log.i("Service",(String)msg.obj);
    }
}