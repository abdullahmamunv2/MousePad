package com.example.charlie.mouse;

/**
 * Created by charlie on 3/17/2016.
 */
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class client extends Thread {

    private String host;
    private int port;
    private DatagramSocket clientSocket;
    private DatagramPacket packet;
    InetAddress IPAddress ;
    public ArrayList<String> list=new ArrayList<>();
    public client(String host,int port ) throws UnknownHostException {
        this.host=host;
        this.port=port;
        //packet=new DatagramPacket("asdf".getBytes(),0);
        //packet.setAddress(InetAddress.getByName(this.host));
        //packet.setPort(this.port);
        IPAddress = InetAddress.getByName(host);
        try {
            //Log.e(packet.toString(),packet.toString());
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            Log.e("clientSocket NUll",e.getMessage());
        }
        this.start();

    }

    public void run(){
        while (true) {
            if(list.size()!=0){
                String msg=list.remove(0);
                byte[] sendData;
                if (msg != null)
                    sendData = msg.getBytes();
                else
                    sendData = "test".getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, this.port);
                try {

                    clientSocket.send(sendPacket);
                    Log.e("send", "send");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    if (clientSocket == null)
                        Log.e("NULL", "NULL");
                    else {
                        Log.e("asdf", ex.getMessage());
                    }

                }
            }

        }
    }


}
