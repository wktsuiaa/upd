package com.example.chorus.udp_test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class RunServer extends Service {

    @Override
    public void onCreate() {


        Log.i("Service","RunServer service -> onCreate, Thread: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg="RunServer service -> onCreate, Thread: "+Thread.currentThread().getName();
        Toast temp=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
        temp.show();
        Log.i("Service","RunServer service -> onStartCommand, startId:" + startId +", Thread"+ Thread.currentThread().getName());





        new Thread(new Server()){}.start();


        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.i("Service", "RunServer service -> onDestroy, Thread: " + Thread.currentThread().getName());
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

}


