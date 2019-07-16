package com.jlkf.text.textapp.network.serverSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Executors;


/**
 * SocketClient.java: 对Socket进行简单接口封装，便于使用。
 * <p>
 * 用法：
 * client = new SocketClient(print, ipString, port);	// 创建客户端Socket操作对象
 * client.start();										// 连接服务器
 * client.Send(data);									// 发送信息
 * client.disconnect();									// 断开连接
 * <p>
 * ----- 2019-6-18 下午5:36:25 scimence
 */
public class SocketClient {

    public String ipString = SocketConfig.IP;   // 服务器端ip
    public int port = SocketConfig.PORT;                // 服务器端口

    public static SocketClient socketClient;
    public Socket socket;
    public SocketCallBack call;                // 数据接收回调方法

    public SocketCallBack getCall() {
        return call;
    }

    public void setCall(SocketCallBack call) {
        this.call = call;
    }

    //同步锁，单例模式，保证多线程访问的安全
    public static synchronized SocketClient getInstance() {
        if (socketClient == null) {
            synchronized (SocketClient.class) {
                if (socketClient == null) {
                    socketClient = new SocketClient();
                }
            }
        }
        return socketClient;
    }

    public SocketClient(SocketCallBack print, String ipString, int port) {
        this.call = print;
        if (ipString != null) this.ipString = ipString;
        if (port >= 0) this.port = port;
    }

    public SocketClient() {
        ipString = SocketConfig.IP;
        port = SocketConfig.PORT;
    }

    /**
     * 创建Socket并连接
     */
    public void start() {
        if (socket != null && socket.isConnected()) return;
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket == null) {
                        InetAddress ip = InetAddress.getByName(ipString);
                        socket = new Socket(ip, port);

                        if (getCall() != null) getCall().Print("服务器已连接 -> " + ip + ":" + port);
                    }
                } catch (Exception ex) {
                    if (getCall() != null) getCall().Print("连接服务器失败 " + ex.toString()); // 连接失败
                }

                // Socket接收数据
                try {
                    if (socket != null) {
                        InputStream inputStream = socket.getInputStream();
                        // 1024 * 1024 * 3 = 3145728
                        byte[] buffer = new byte[3145728];        // 3M缓存
                        int len = -1;
                        while (socket.isConnected() && (len = inputStream.read(buffer)) != -1) {
                            String data = new String(buffer, 0, len);

                            // 通过回调接口将获取到的数据推送出去
                            if (getCall() != null) {
                                getCall().Print("接收信息 -> " + data);
                            }
                        }

                    }
                } catch (Exception ex) {
                    if (getCall() != null) getCall().Print("接收socket信息失败" + ex.toString()); // 连接失败
                    socket = null;
                }
            }
        });
    }


    /**
     * 发送信息
     */
    public void Send(String data) {
        new Thread(() -> {
            try {
                if (socket != null && socket.isConnected()) {
                    byte[] bytes = data.getBytes();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(bytes);

                    if (getCall() != null) getCall().Print("发送信息 -> " + data);
                } else {
                    if (getCall() != null) getCall().Print("未连接服务器！清先连接后，再发送。");
                }
            } catch (Exception ex) {
                if (getCall() != null) getCall().Print("发送socket信息失败！" + ex);
            }
        }).start();

    }

    /**
     * 断开Socket
     */
    public void disconnect() {
        try {
            if (socket != null && socket.isConnected()) {
                socket.close();
                socket = null;
                if (getCall() != null) getCall().Print("服务器已断开！ ");
            }
        } catch (Exception ex) {
            if (getCall() != null) getCall().Print("断开socket失败!");
        }
    }
}
