package com.example.chorus.udp_test;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MultiClient implements Runnable {

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

            while(true) {
                InetAddress group = InetAddress.getByName("228.5.6.7");
                MulticastSocket s = new MulticastSocket(6799);
                s.joinGroup(group);

                // get their responses!
                byte[] buf = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buf, buf.length);
                s.receive(reply);
                // OK, I'm done talking - leave the group...

                String output = new String(reply.getData(), reply.getOffset(), reply.getLength());
                showResult("Received msg: " + output + "\nFrom: " + reply.getAddress() + " At Port: " + reply.getPort());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
