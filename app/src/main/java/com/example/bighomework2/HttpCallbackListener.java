package com.example.bighomework2;

public interface HttpCallbackListener {
    void onFinish(String respone);

    void onError(Exception e);
}
