package com.example.bighomework2;

public class DataUtil { private static String start = "S";
    private static String end= "E";

    public static void show(String[] vos){//展示数据，用于检查
        // 数组不为空
        System.out.println(vos[0]+vos[1]+vos[2]+vos[3]+vos[4]);

        if(vos!=null||vos.length!=0){
            System.out.println("--------------------解析过程--------------------- ");
            // 数据
            String acty=vos[1];
            System.out.println("动作: "+ acty);
            // 数据
            String flag=vos[2];
            System.out.println("标识符: " + flag);
            // 数据
            String data=vos[3];
            System.out.println("数据: " + data);
        }
    }

    public static String[] analysis(String input){                                                         //解析udp包
        String vos[] = new String[5];
        try {
            System.out.println("--------------udp解析线程运行了--------------");
            // 如果堵塞队列中存在数据就将其输出
            if (input.length() > 0) {
                vos = input.split(":", -1);//-1保留空值
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return vos;
        }
    }

    public static String encapsulate(String acty, String flag, String data){                                //封装
        String result = new String();
        try {
            //System.out.println("--------------udp封装运行了--------------");
            result = start + ":" + acty + ":" + flag + ":" + data + ":" + end;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
