package com.jlkf.text.textapp.network.webSocket;

/**
 *
 * Socket通信接口
 */
public interface IReceiveMessage {
    void onConnectSuccess();// 连接成功

    void onConnectFailed();// 连接失败

    void onClose(); // 关闭

    void onMessage(String text);
}