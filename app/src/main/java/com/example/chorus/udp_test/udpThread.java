package com.example.chorus.udp_test;

import android.util.Log;

import com.example.chorus.udp_test.Service.Server;

public class udpThread extends Thread {
    private Runnable udp;
    public udpThread(Runnable target) {
        super(target);
        Log.i("Thread","Service Thread Id: "+ this.getId());
        this.udp=target;
    }

    @Override
    public synchronized void start() {
        super.start();

    }
    @Override
    public void interrupt() {

        String[] temp=udp.getClass().toString().split("\\.");
        String target=temp[temp.length-1];

        Log.i("Thread","The class of runnable is "+target);

        if(target.equals("Server"))
        {
            Server.stop();
        }
        else if(target.equals("Client")){
            Client.stop();
        }
        super.interrupt();
    }
}
