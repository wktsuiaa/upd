package com.example.chorus.udp_test.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.chorus.udp_test.Service.helper.UThread;

public class RunServer extends Service {
    private UThread serve;
    private Toast prompt;
    @Override
    public void onCreate() {
        Log.i("Service","RunServer service -> onCreate, Thread: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","RunServer service -> onStartCommand, startId:" + startId +", Thread"+ Thread.currentThread().getName());

        serve= new UThread(new Server());
        serve.start();
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {

        prompt=Toast.makeText(getApplicationContext(),"RunServer service -> onDestroy, Thread: " + Thread.currentThread().getName(),Toast.LENGTH_LONG);
        prompt.show();

        serve.interrupt();
        serve=null;
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

}


