package com.example.bighomework2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class UDPSender {                                                            //全员静态
    private DatagramSocket socket=null;										//UDP套接字,用于发送和接受数据包
    private List<DatagramPacket> datagramPackets;
    private int serverPort=4399;												//服务器端口
    private InetAddress address=null;										//封装IP

    public UDPSender(){
        datagramPackets=new ArrayList<DatagramPacket>();
    }

    public void sendData(final List<String> data) {//发送账号
        try {
            socket=new DatagramSocket();
            address= InetAddress.getByName("192.168.43.79");//据主机获取对应的InetAddress对象
            makeDP(data);
            for(int i=0;i<datagramPackets.size();i++)
            {
                socket.send(datagramPackets.get(i));//循环发送数据包
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //socket.close();
    }

    public void makeDP(List<String> data){
        datagramPackets.clear();//清除之前所有的数据
        for(int i=0;i<data.size();i++){
            datagramPackets.add(new DatagramPacket(data.get(i).getBytes(),data.get(i).getBytes().length,address,serverPort));//打包并添加
        }

    }

}
