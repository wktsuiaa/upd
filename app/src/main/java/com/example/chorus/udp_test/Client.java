package com.example.chorus.udp_test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client implements Runnable {
    private static DatagramSocket client = null;
    private final static int timeout=10000;
    private int port = MainActivity.SERVERPORT;
    private String ipAddress =MainActivity.ip.getText().toString();
    private String msgOut=MainActivity.msg.getText().toString();

    public static void stop(){
        client.close();
        if(client.isClosed()){
            Log.i("Client","Client Closed");
        }
    }

    /*
    @Server: is the target's ip address
    @port: is the target's port listening

 */
    public void run() {
        try{


            InetAddress server = InetAddress.getByName(ipAddress);

            client = new DatagramSocket(4000);
            client.setSoTimeout(timeout);

            while(true) {
                Log.i("Client","Client is sending to " + ipAddress);


                //Get the input from the editText
                String result = msgOut;
                byte[] message = result.getBytes();

                DatagramPacket packet = new DatagramPacket(message, message.length, server, port);
                Log.i("Client","Client: Sending " + new String(message));

                client.send(packet);
                Log.i("Client","Client: Message sent");

                //Receive Package
                byte[] recvBuf = new byte[1024];
                DatagramPacket reply = new DatagramPacket(recvBuf, recvBuf.length);

                client.receive(reply);
                String output = new String(reply.getData(), reply.getOffset(), reply.getLength());

                Log.i("Client","Received msg: " + output + "From: " + reply.getAddress() + " At Port: " + reply.getPort());

                //client.close();
                Thread.sleep(1000);

            }

        } catch (UnknownHostException e) {
            Log.e("Error",Log.getStackTraceString(e));
        } catch (SocketTimeoutException e){
            Log.i("Client","Client: Time out\n");
            client.close();
        } catch (IOException e) {
            Log.e("Error",Log.getStackTraceString(e));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
