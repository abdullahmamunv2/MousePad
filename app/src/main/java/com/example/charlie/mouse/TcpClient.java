package com.example.charlie.mouse;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by charlie on 4/11/2016.
 */
public class TcpClient extends Thread {

    private String host;
    private int port;
    private Socket clientSocket;
    private DatagramPacket packet;
    DataOutputStream outToServer;
    BufferedReader inFromServer ;
    InetAddress IPAddress ;
    public ArrayList<String> list=new ArrayList<>();
    public TcpClient(String host,int port ) throws UnknownHostException {
        this.host=host;
        this.port=port;
        //packet=new DatagramPacket("asdf".getBytes(),0);
        //packet.setAddress(InetAddress.getByName(this.host));
        //packet.setPort(this.port);
        IPAddress = InetAddress.getByName(host);
            //Log.e(packet.toString(),packet.toString());
        try {
            clientSocket = new Socket(IPAddress,port);
        } catch (IOException e) {
            Log.e("error",e.getMessage());
        }

        ConfigureStream();
        this.start();

    }

    public void ConfigureStream(){
        try {
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void run(){
        while (true) {
            if(list.size()!=0){
                String msg=list.remove(0);
                try {
                    outToServer.writeBytes(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}

