package com.jlkf.text.textapp.network.util;

import okhttp3.RequestBody;

/**
 * Post请求J
 */
public class PostJson{
    public static RequestBody toRequestBody(String json){
        return  RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
    }
}
