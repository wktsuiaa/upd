package com.example.chorus.udp_test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class mHandler extends Handler {

    public static void showResult(String output){
        Handler mHandler=MainActivity.mHandler;
        Message msg=Message.obtain();
        msg.what=1;
        msg.obj=output;
        mHandler.sendMessage(msg);
        int port=MainActivity.SERVERPORT;
    }

    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        //result.append((String)msg.obj+"\n");
        Log.i("Service",(String)msg.obj);
    }
}