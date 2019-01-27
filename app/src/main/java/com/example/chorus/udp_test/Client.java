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
    DatagramSocket client = null;
    private final static int timeout=10000;
    int port = MainActivity.SERVERPORT;

    protected void showResult(String output){
        Handler mHandler=MainActivity.mHandler;
        Message msg=Message.obtain();
        msg.what=1;
        msg.obj=output;
        mHandler.sendMessage(msg);
        int port=MainActivity.SERVERPORT;
    }


    @Override
    public void run() {
        try{
            //Send Package
            /*
                @Server: is the target's ip address
                @port: is the target's port listening

             */
            InetAddress server = InetAddress.getByName(MainActivity.ip.getText().toString());

            client=new DatagramSocket(4000);
            client.setSoTimeout(timeout);

            showResult("Client is sending to "+MainActivity.ip.getText().toString());

            String result=MainActivity.msg.getText().toString();
            byte[] message=result.getBytes();

            DatagramPacket packet=new DatagramPacket(message,message.length,server,port);
            showResult("Client: Sending "+ new String(message)+"\n");

            client.send(packet);
            showResult("Client: Message sent\n");

            //Receive Package
            byte[] recvBuf = new byte[1024];
            DatagramPacket reply =new DatagramPacket(recvBuf,recvBuf.length);

            client.receive(reply);
            String output=new String (reply.getData(),reply.getOffset(),reply.getLength());

            showResult("Received msg: "+output + "\nFrom: "+reply.getAddress()+ " At Port: "+reply.getPort());

            client.close();

        } catch (UnknownHostException e) {
            Log.e("Error",Log.getStackTraceString(e));
        } catch (SocketTimeoutException e){
            showResult("Client: Time out\n");
            client.close();
        } catch (IOException e) {
            Log.e("Error",Log.getStackTraceString(e));
        }
    }
}
