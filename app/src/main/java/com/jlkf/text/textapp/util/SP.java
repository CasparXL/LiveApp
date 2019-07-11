package com.jlkf.text.textapp.util;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.util.Set;

public class SP {

    private static final String TAG = "PreferencesUtil";
    private static String defaultName = SP.class.getSimpleName();
    private static Context context;


    private static SharedPreferences getPreferences(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 初始化
     */
    public static void init(Application application) {
        context = application;
    }

    public static String getDefaultName() {
        return defaultName;
    }


    public static void setDefaultName(String name) {
        defaultName = name;
    }


    public static boolean get(String key, boolean defValue) {
        return get(defaultName, key, defValue);
    }


    public static int get(String key, int defValue) {
        return get(defaultName, key, defValue);
    }


    public static float get(String key, float defValue) {
        return get(defaultName, key, defValue);
    }


    public static long get(String key, long defValue) {
        return get(defaultName, key, defValue);
    }

    public static String get(String key, String defValue) {
        return get(defaultName, key, defValue);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> get(String key, Set<String> defValue) {
        return get(defaultName, key, defValue);
    }


    public static boolean get(String name, String key, boolean defValue) {
        return getPreferences(name).getBoolean(key, defValue);
    }


    public static int get(String name, String key, int defValue) {
        return getPreferences(name).getInt(key, defValue);
    }


    public static float get(String name, String key, float defValue) {
        return getPreferences(name).getFloat(key, defValue);
    }


    public static long get(String name, String key, long defValue) {
        return getPreferences(name).getLong(key, defValue);
    }


    public static String get(String name, String key, String defValue) {
        Log.e(TAG, "get: " + key + ":" + getPreferences(name).getString(key, defValue));
        return getPreferences(name).getString(key, defValue);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> get(String name, String key, Set<String> defValue) {
        return getPreferences(name).getStringSet(key, defValue);
    }


    public static void put(String key, boolean value) {
        put(defaultName, key, value);
    }


    public static void put(String key, int value) {
        put(defaultName, key, value);
    }


    public static void put(String key, float value) {
        put(defaultName, key, value);
    }


    public static void put(String key, long value) {
        put(defaultName, key, value);
    }


    public static void put(String key, String value) {
        put(defaultName, key, value);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void put(String key, Set<String> value) {
        put(defaultName, key, value);
    }


    public static void put(String name, String key, boolean value) {
        getPreferences(name).edit().putBoolean(key, value).apply();
    }


    public static void put(String name, String key, int value) {
        getPreferences(name).edit().putInt(key, value).apply();

    }


    public static void put(String name, String key, float value) {
        getPreferences(name).edit().putFloat(key, value).apply();
    }


    public static void put(String name, String key, long value) {
        getPreferences(name).edit().putLong(key, value).apply();
    }


    private static void put(final String name, final String key, final String value) {
        getPreferences(name).edit().putString(key, value).apply();

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void put(final String name, final String key, final Set<String> value) {
        getPreferences(name).edit().putStringSet(key, value).apply();

    }


    public static void remove(String key) {
        remove(defaultName, key);
    }


    public static void remove(String name, String key) {
        getPreferences(name).edit().remove(key).apply();
    }


    public static void clear() {
        clear(defaultName);
    }


    public static void clear(String name) {
        getPreferences(name).edit().clear().apply();
    }


    /*********************************app信息*************************************/

    /***是否第一次打开app**/
    public static boolean getFirstOpen() {
        return get("FirstOpen", false);
    }

    /***是否第一次打开app**/
    public static void setFirstOpen(boolean id) {
        put("FirstOpen", id);
    }

    /***MAC防火墙是否打开**/
    public static boolean getMacOpen() {
        return get("MacOpen", false);
    }

    /***MAC防火墙是否打开**/
    public static void setMacOpen(boolean id) {
        put("MacOpen", id);
    }

    /***IP防火墙是否打开**/
    public static boolean getIpOpen() {
        return get("IpOpen", false);
    }

    /***IP防火墙是否打开**/
    public static void setIpOpen(boolean id) {
        put("IpOpen", id);
    }

    /***IP防火墙是否打开**/
    public static boolean getUrlOpen() {
        return get("UrlOpen", false);
    }

    /***IP防火墙是否打开**/
    public static void setUrlOpen(boolean id) {
        put("UrlOpen", id);
    }

    /***WAN接入类型 0,动态IP 1，静态IP 2，PPPOE账号 3，桥模式**/
    public static int getWifiConnectType() {
        return get("WifiConnectType", 0);
    }

    /***WAN接入类型**/
    public static void setWifiConnectType(int id) {
        put("WifiConnectType", id);
    }


}