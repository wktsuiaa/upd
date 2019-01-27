package com.example.chorus.udp_test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {

    int port = MainActivity.SERVERPORT;
    String ip=MainActivity.ip.getText().toString();
    private final static int timeout=10000;

    protected void showResult(String output){
        Handler mHandler=MainActivity.mHandler;
        Message msg=Message.obtain();
        msg.what=1;
        msg.obj=output;
        mHandler.sendMessage(msg);
    }

    @Override
    public void run() {


        try {
            DatagramSocket server = new DatagramSocket(port);

            showResult("Server Started at Port: "+ server.getPort());
            showResult("Server is running");

            while(true){
                byte[] recvBuf = new byte[1024];
                DatagramPacket result =new DatagramPacket(recvBuf,recvBuf.length);


                //Received msg
                server.receive(result);
                String output=new String (result.getData(),result.getOffset(),result.getLength());


                showResult("Received msg: "+output + "\nFrom: "+result.getAddress()+ " At Port: "+result.getPort());

                //Response to client
                byte[] replybuf = new byte[1024];
                String response="Response from Server :"+ip+" at Port: "+server.getLocalPort();
                replybuf=response.getBytes();

                DatagramPacket reply = new DatagramPacket(replybuf,replybuf.length,result.getAddress(),result.getPort());
                server.send(reply);
                showResult("Server replied");

            }

        } catch (SocketException e) {
            Log.e("Error",Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e("Error",Log.getStackTraceString(e));
        }
    }
}
