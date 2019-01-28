package com.example.chorus.udp_test.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.chorus.udp_test.Client;
import com.example.chorus.udp_test.udpThread;

public class RunClient extends Service {
    private udpThread client;
    private Toast prompt;
    @Override
    public void onCreate() {
        Log.i("Service","RunServer service -> onCreate, Thread: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","RunServer service -> onStartCommand, startId:" + startId +", Thread"+ Thread.currentThread().getName());

        client= new udpThread(new Client());
        client.start();
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {

        prompt=Toast.makeText(getApplicationContext(),"RunServer service -> onDestroy, Thread: " + Thread.currentThread().getName(),Toast.LENGTH_LONG);
        prompt.show();

        client.interrupt();
        client=null;
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
