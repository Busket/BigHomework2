package com.example.bighomework2;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonParse {
    /**
     * 解析Json数据
     *
     * @param urlPath
     * @return mlists
     * @throws Exception
     */

    public static List<User> getListUsers(String urlPath) throws Exception {
        List<User> mlists = new ArrayList<User>();
        byte[] data = readParse(urlPath);
        JSONArray array = new JSONArray(new String(data));
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = array.getJSONObject(i);
            String Acount_Number = item.getString("Acount_Number");
            String Password = item.getString("Password");
            String Name = item.getString("Name");
            //String Avatar = item.getString("Avatar");
            int Credibility = item.getInt("Credibility");

            mlists.add(new User(Acount_Number, Password, Name,Credibility));
        }
        return mlists;
    }

    public static User getListUser(String urlPath) throws Exception {
        User user = new User();
        byte[] data = readParse(urlPath);
        JSONArray array = new JSONArray(new String(data));
        JSONObject jsonObject=array.getJSONObject(0);

        String State=jsonObject.getString("State");
        user.State=State;
        String Acount_Number = jsonObject.getString("Acount_Number");
        user.Acount_Number=Acount_Number;
        String Password = jsonObject.getString("Password");
        user.Password=Password;
        String Name = jsonObject.getString("Name");
        user.Name=Name;
        //String Avatar = jsonObject.getString("Avatar");
        int Credibility = jsonObject.getInt("Credibility");
        user.Credibility=Credibility;

        return user;
    }

    /**
     * 从指定的url中获取字节数组
     *
     * @param urlPath
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
