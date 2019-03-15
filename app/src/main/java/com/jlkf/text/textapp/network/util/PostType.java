package com.jlkf.text.textapp.network.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Retrofit的Post请求工具类
 */
public class PostType {
    /**
     * @param value
     * @return
     */
    public static RequestBody toRequestBody(String value) {
        if (value != null)
        return RequestBody.create(MediaType.parse("text/plain"), value);
        else
            return null;
    }

    /**
     * 上传图片类型
     *
     * @param value
     * @return
     */
    public static RequestBody toRequestBody(File value) {
        if (value != null)
            return RequestBody.create(MediaType.parse("multipart/form-data"), value);
        else
            return null;
    }

    /**
     * @param requestDataMap Map<String, String> requestDataMap这里面放置上传数据的键值对。
     * @return
     */
    public static Map<String, RequestBody> toRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    /**
     * @param files
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }


}
