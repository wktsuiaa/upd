package com.example.chorus.udp_test;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    public static final String SERVERIP ="192.168.232.2";
    public static final int SERVERPORT = 5100;

    public static EditText ip;
    public static TextView result;
    public static EditText msg;
    public static Switch server;
    public static Switch client;

    public static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initit_item();
        get_IP();


    }


    protected void initit_item() {
        ip = findViewById(R.id.ipAdress);
        msg= findViewById(R.id.receive);
        result=findViewById(R.id.rmsg);
        server=findViewById(R.id.udp_server);
        client=findViewById(R.id.udp_client);

        mHandler=new Handler(Looper.myLooper()){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                result.append((String)msg.obj+"\n");
            }
        };
    }

    protected void get_IP() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipA = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        ip.setText(ipA.toString());

    }

    public void startClient(View view) {
        new Thread(new Client()).start();
    }

    public void startServer(View view) {
        new Thread(new Server()).start();
    }

    public void clearText(View view) {
        result.setText("");
    }

    public void start_service(View view) {
        if(server.isChecked()){
            result.append("Starting Server\n");

            Intent startIntent=new Intent(getApplicationContext(),RunServer.class);
            startIntent.putExtra("from","Main");
            startService(startIntent);
        }

        if(!server.isChecked()){

            Intent stopIntent = new Intent(getApplicationContext(),RunServer.class);
            stopService(stopIntent);
            result.append("End Server\n");

        }

    }
}
