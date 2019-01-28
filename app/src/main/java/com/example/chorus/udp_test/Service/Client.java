package com.example.chorus.udp_test.Service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chorus.udp_test.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class Client implements Runnable {
    private static DatagramSocket client = null;
    private final static int timeout = 10000;
    private int port = MainActivity.SERVERPORT;

    //Input and output
    private String ipAddress = MainActivity.ip.getText().toString();
    private String msgOut = MainActivity.msg.getText().toString();

    public static void stop() {
        client.close();
        if (client.isClosed()) {
            Log.i("Client", "Client Closed");
        }
    }

    /*
    @Server: is the target's ip address
    @port: is the target's port listening

 */
    public void run() {
        try {
            final InetAddress server = InetAddress.getByName(ipAddress);

            client = new DatagramSocket(4000);
            client.setSoTimeout(timeout);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    Log.i("Client", "Client is sending to " + ipAddress);


                    //Get the input from the editText
                    String result = msgOut;
                    byte[] message = result.getBytes();

                    DatagramPacket packet = new DatagramPacket(message, message.length, server, port);
                    Log.i("Client", "Client: Sending " + new String(message));

                    try {
                        client.send(packet);

                        Log.i("Client", "Client: Message sent");

                        //Receive Package
                        byte[] recvBuf = new byte[1024];
                        DatagramPacket reply = new DatagramPacket(recvBuf, recvBuf.length);

                        client.receive(reply);
                        String output = new String(reply.getData(), reply.getOffset(), reply.getLength());

                        Log.i("Client", "Received msg: " + output + "From: " + reply.getAddress() + " At Port: " + reply.getPort());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0,100000);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
