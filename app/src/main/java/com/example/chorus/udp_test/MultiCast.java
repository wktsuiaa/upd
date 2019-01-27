package com.example.chorus.udp_test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MultiCast implements Runnable {
    @Override
    public void run() {
        // join a Multicast group and send the group salutations

        String msg = "Hello";
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7");
            MulticastSocket s = new MulticastSocket();
            s.joinGroup(group);
            DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                    group, 6799);
            s.send(hi);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
