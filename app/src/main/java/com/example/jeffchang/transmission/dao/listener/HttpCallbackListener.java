package com.example.jeffchang.transmission.dao.listener;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
