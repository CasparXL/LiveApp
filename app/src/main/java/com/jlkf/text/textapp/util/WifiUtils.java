package com.jlkf.text.textapp.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.hjq.toast.ToastUtils;
import com.jlkf.text.textapp.app.BaseApplication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * "浪小白" 创建 2019/7/10.
 * 界面名称以及功能: 获取WIFI一系列工具
 */
public class WifiUtils {
    public static String getWifiName(Context context) {
        WifiManager wifiMgr = (WifiManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr != null) {
            WifiInfo info = wifiMgr.getConnectionInfo();
            return info != null ? info.getSSID() : "";
        } else {
            return "";
        }
    }

    /**
     * 获取SSID
     * @return WIFI 的SSID
     */
    public static String getWifiSSID() {
        String ssid = "SSID名称";
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            WifiManager mWifiManager = (WifiManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
            ConnectivityManager connManager = (ConnectivityManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connManager != null;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo() != null) {
                    return networkInfo.getExtraInfo().replace("\"", "");
                }
            }
        }
        return ssid;
    }
    public static String getIPAddress() {
        NetworkInfo info = ((ConnectivityManager) BaseApplication.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager)  BaseApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            ToastUtils.show("当前无网络连接,请在设置中打开网络");
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * 前往WIFI设置界面
     */
    public static void start(Context context) {
        Intent view = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(view);
    }

}
