package com.jlkf.text.textapp.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 基于Gson的一个工具类
 */
public class GsonUtil {
    /**
     * 将一个对象转换为JSON格式的串
     *
     * @param object 任意对象
     *
     * @return JSON格式的字符串
     */
    public static String convertVO2String(Object object) {
        try {
            Gson gson = new Gson();
            return gson.toJson(object);
        } catch (Exception e) {
            LogUtil.e(e.toString());
            return null;
        }
    }
 
    /**
     * 将一个JSON格式的字符串转换为Java对象
     *
     * @param jsonStr   要转换的JSON格式的字符串
     * @param targetClass 要将这个JSON格式的字符串转换为什么类型的对象
     *
     * @return 转换之后的Java对象
     */
    public static <T> T convertString2Object(String jsonStr, Class<T> targetClass) {
        try {
            Gson gson = new Gson();
            if (isJson(jsonStr)) {
                return gson.fromJson(jsonStr, targetClass);
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("Demo", "convertString2Object \r\n  error = \r\n    "  + Log.getStackTraceString(e));
            return null;
        }
    }
 
    /**
     * 将一个json转换成一个集合对象
     *
     * @param jsonStr   要转换的JSON格式的字符串
     * @param typeToken TypeToken<这里指定集合类型和泛型信息>
     *
     * @return 转换之后的集合对象
     */
    public static <T> T convertString2Collection(String jsonStr, TypeToken<T> typeToken) {
        try {
            Gson gson = new Gson();
            if (isJson(jsonStr)) {
                T t = gson.fromJson(jsonStr, typeToken.getType());
                return t;
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("Demo", "convertString2Collection \r\n  error = \r\n    "  + Log.getStackTraceString(e));
            return null;
        }
    }
 
    public static boolean isJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject = null;
            return true;
        } catch (JSONException e1) {
            Log.e("Demo", "isJson \r\n  error = \r\n    "  + Log.getStackTraceString(e1));
            return false;
        }
    }
 
    public static ArrayList<String> convertJson2Array(String str) {
        ArrayList<String> strs = new ArrayList<String>();
        try {
            JSONArray arr = new JSONArray(str);
            for (int i = 0; i < arr.length(); i++) {
                strs.add(arr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strs;
    }
}
