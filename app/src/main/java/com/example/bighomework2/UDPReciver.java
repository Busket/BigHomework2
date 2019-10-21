package com.example.bighomework2;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class UDPReciver {
    public DatagramSocket socket;
    public static final int PhonePort = 5554;//手机端口号
    public DatagramPacket packet;
    public volatile boolean stopReceiver;
    String filePath = "/sdcard/AIUI/devices.txt";
    public String receive;

    public void receiveMessage() {
        new Thread() {
            public void run() {
                try {
                    socket = new DatagramSocket(PhonePort);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                byte[] receBuf = new byte[1024];
                packet = new DatagramPacket(receBuf, receBuf.length);
                while (!stopReceiver) {
                    try {
                        socket.receive(packet);
                        receive = new String(packet.getData(), 0, packet.getLength(), "utf-8");
                        //Log.e("zziafyc", "收到的内容为：" + receive);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    }
