package com.example.bighomework2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPSender{
    Socket socket;
    String str;
    String s2=null;

    public TCPSender(Socket socket){
        this.socket=socket;
    }
    public void sendData(String data){
        this.str=data;
        try {
            socket= new Socket("192.168.43.86", 4398);
            Log.i("Client","客户端连接成功");
            final BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bw.write(str);
                        bw.newLine();
                        bw.flush();
                    }
                    catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
            }).start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getData(){
        try {
            Log.i("Client","开始接收数据！");
            final BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new Runnable() {
                public void run() {
                    try {
                        do{
                            s2=br.readLine();
                            Log.d("Client","接受到服务器端发来的消息:"+s2);
//                            if(s2!=null)
//                               Toast.makeText(context,s2,Toast.LENGTH_LONG).show();
                        }while((s2)!=null);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }).start();
//            try {
//                Thread.sleep(1000);
//            }
//            catch (InterruptedException e){
//                e.printStackTrace();
//            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
