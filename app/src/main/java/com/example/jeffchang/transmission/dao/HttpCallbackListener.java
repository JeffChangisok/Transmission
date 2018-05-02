package com.example.jeffchang.transmission.dao;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
