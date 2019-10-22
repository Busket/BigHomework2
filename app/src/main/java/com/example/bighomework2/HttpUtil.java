package com.example.bighomework2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
//    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;//初始化http连接
//                try {
//                    URL url = new URL(address);
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
//                    InputStream inputStream = connection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuilder respone = new StringBuilder();
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        respone.append(line);
//                    }
//                    if (listener!=null){
//                        listener.onFinish(respone.toString());
//                    }
//                    //return respone.toString();//这里返回get到的数据
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    if(listener!=null){
//                        listener.onError(e);
//                    }
//                    //return e.getMessage();
//                } finally {
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//    }
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
    //登录
    static void loginWithOkHttp(final String address, final String account, final String password, final okhttp3.Callback callback){

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("loginAccount",account);
                    obj.put("loginPassword",password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                MediaType type = MediaType.parse("application/json;charset=utf-8");
                RequestBody body = new FormBody.Builder()
                .add("obj",""+obj.toString())
               .build();
                //RequestBody RequestBody2 = RequestBody.create(type,""+obj.toString());
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            // 指定访问的服务器地址
                            .url(address).post(body)
                            .build();
                    client.newCall(request).enqueue(callback);//设置回复
                    //Response response = client.newCall(request).execute();
                    //String responseData = response.body().string();
                    //parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("loginAccount",account)
//                .add("loginPassword",password)
//                .build();
//        Request request = new Request.Builder()
//                .url(address)
//                .post(body)
//                .build();
//        Log.i("Send",account+"  "+password);
//        client.newCall(request).enqueue(callback);
    }
    //注册
    static void registerWithOkHttp(final String address, final String account, final String password,final String username, final okhttp3.Callback callback){

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("registerAccount",account);
                    obj.put("registerPassword",password);
                    obj.put("registerName",username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = new FormBody.Builder()
                        .add("obj",""+obj.toString())
                        .build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            // 指定访问的服务器地址
                            .url(address).post(body)
                            .build();
                    client.newCall(request).enqueue(callback);//设置回复

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("registerAccount",account)
//                .add("registerPassword",password)
//                .build();
//        Request request = new Request.Builder()
//                .url(address)
//                .post(body)
//                .build();
//        client.newCall(request).enqueue(callback);
    }

    private static void parseJSONWithJSONObject(String jsonData) {//解析json包

    }

}

