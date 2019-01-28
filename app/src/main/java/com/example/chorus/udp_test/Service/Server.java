package com.example.chorus.udp_test.Service;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chorus.udp_test.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {

    int port = MainActivity.SERVERPORT;

    private final static int timeout=10000;
    private static DatagramSocket server;


    public static void stop(){

        server.close();

        if(server.isClosed()){
            Log.i("Server","Server Closed");
        }
    }

    @Override
    public void run() {


        try {
            server = new DatagramSocket(port);

            Log.i("Server","Server Started at Port: "+ port);
            Log.i("Server","Server is running\n");

            while(true){
                byte[] recvBuf = new byte[1024];
                DatagramPacket result =new DatagramPacket(recvBuf,recvBuf.length);


                //Received msg
                server.receive(result);
                String output=new String (result.getData(),result.getOffset(),result.getLength());


                Log.i("Server","Received msg: "+output + "\nFrom: "+result.getAddress()+ " At Port: "+result.getPort());

                //Response to client
                byte[] replybuf = new byte[1024];
                String response="Response from Server :"+result.getAddress()+" at Port: "+server.getLocalPort();
                replybuf=response.getBytes();

                DatagramPacket reply = new DatagramPacket(replybuf,replybuf.length,result.getAddress(),result.getPort());
                server.send(reply);
                Log.i("Server","Server replied");

            }

        } catch (SocketException e) {
            Log.e("Error",Log.getStackTraceString(e));
        } catch (IOException e) {
            Log.e("Error",Log.getStackTraceString(e));
        }
    }
}
