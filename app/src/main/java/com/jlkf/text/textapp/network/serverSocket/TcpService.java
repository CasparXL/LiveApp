package com.jlkf.text.textapp.network.serverSocket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.util.WifiUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by Fiora on 2018/10/24 0024
 */
public class TcpService extends Service {
    public static final String TAG = TcpService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ClientBinder();
    }

    public class ClientBinder extends Binder {
        private int HeartbeatInterval = 3 * 1000; //心跳间隔时间
        private int MAX_NUMBER = 5; //重连最多次数
        private BufferedInputStream bis;
        private BufferedOutputStream bos;
        private ReadThread mReadThread;
        private Handler mHandler = new Handler();
        private Socket mSocket;
        private ExecutorService mExecutorService;
        private int tryCount = 0;//重试次数

        public void startConnect() {
            //在子线程进行网络操作
            // Service也是运行在主线程，千万不要以为Service意思跟后台运行很像，就以为Service运行在后台子线程
            if (mExecutorService == null) {
                mExecutorService = Executors.newCachedThreadPool();
            }
            mExecutorService.execute(connectRunnable);
        }

        private Runnable connectRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    // 建立Socket连接
                    mSocket = new Socket();
                    mSocket.connect(new InetSocketAddress(SocketConfig.IP, SocketConfig.PORT), 10);
                    bis = new BufferedInputStream(mSocket.getInputStream());
                    bos = new BufferedOutputStream(mSocket.getOutputStream());
                    // 创建读取服务器心跳的线程
                    mReadThread = new ReadThread();
                    mReadThread.start();
                    //开启心跳,每隔15秒钟发送一次心跳
                    mHandler.post(mHeartRunnable);
                    tryCount = 1;
                    LogUtil.e("Socket连接建立成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    if (tryCount > MAX_NUMBER) {
                        tryCount++;
                        LogUtil.e("Socket连接建立失败,正在尝试第" + tryCount + "次重连,手机本机IP：" + WifiUtils.getIPAddress() + "错误原因:" + e.getMessage());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mExecutorService.execute(connectRunnable);
                            }
                        }, HeartbeatInterval);
                    } else {
                        LogUtil.e("重连次数达到上限，请检查网络是否正常");
                    }

                }
            }
        };

        public class ReadThread extends Thread {
            @Override
            public void run() {
                int size;
                byte[] buffer = new byte[1024];
                try {
                    while ((size = bis.read(buffer)) != -1) {
                        String str = new String(buffer, 0, size);
                        LogUtil.e("我收到来自服务器的消息: " + str);
                        //收到心跳消息以后，首先移除断连消息，然后创建一个新的60秒后执行断连的消息。
                        //这样每次收到心跳后都会重新创建一个60秒的延时消息，在60秒后还没收到心跳消息，表明服务器已死，就会执行断开Socket连接
                        //在60秒钟内如果收到过一次心跳消息，就表明服务器还活着，可以继续与之通讯。
                        mHandler.removeCallbacks(disConnectRunnable);
                        mHandler.postDelayed(disConnectRunnable, HeartbeatInterval * 40);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e(e.getMessage());
                }
            }
        }

        private Runnable mHeartRunnable = new Runnable() {
            @Override
            public void run() {
                sendData();
            }
        };

        private void sendData() {
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        bos.write("给你一张过去的CD,听听那时我们的爱情！".getBytes());
                        //一定不能忘记这步操作
                        bos.flush();
                        //发送成功以后，重新建立一个心跳消息
                        mHandler.postDelayed(mHeartRunnable, HeartbeatInterval);
                        LogUtil.e("我发送给服务器的消息: 给你一张过去的CD,听听那时我们的爱情！");
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.e("心跳任务发送失败，正在尝试第" + tryCount + "次重连");
                        //mExecutorService.schedule(connectRunnable,HeartbeatInterval, TimeUnit.SECONDS);
                        mExecutorService.execute(connectRunnable);
                    }
                }
            });
        }

        private Runnable disConnectRunnable = new Runnable() {
            @Override
            public void run() {
                disConnect();
            }
        };

        private void disConnect() {
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        LogUtil.e("正在执行断连: disConnect");
                        //执行Socket断连
                        mHandler.removeCallbacks(mHeartRunnable);
                        if (mReadThread != null) {
                            mReadThread.interrupt();
                        }

                        if (bos != null) {
                            bos.close();
                        }

                        if (bis != null) {
                            bis.close();
                        }

                        if (mSocket != null) {
                            mSocket.shutdownInput();
                            mSocket.shutdownOutput();
                            mSocket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}