package com.jlkf.text.textapp.net.util.httptype;

import com.google.gson.Gson;

import okhttp3.RequestBody;

/**
 * Post请求J
 */
public class PostJson {
    public static RequestBody toRequestBody(String json) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static String toJsonString(Object json) {
        return new Gson().toJson(json);
    }
}
