package com.jlkf.text.textapp.network;

import com.google.gson.GsonBuilder;
import com.jlkf.text.textapp.network.interceptor.Logger;
import com.jlkf.text.textapp.network.interceptor.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络访问
 */
public class RetrofitFactory {
    //访问超时
    private static final long TIMEOUT = 30;

    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            // 打印接口信息，方便接口调试
            .addInterceptor(new LoggingInterceptor(new Logger()).setLevel(LoggingInterceptor.Level.BODY)).
                    connectTimeout(TIMEOUT, TimeUnit.SECONDS).
                    readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static ServiceSApi retrofitService = new Retrofit.Builder().baseUrl(Http.ROOT)
            // 添加Gson转换器
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(ServiceSApi.class);

    //     获得RetrofitService对象
    public static ServiceSApi getInstance() {
        return retrofitService;
    }

    public static ServiceSApi getInstance(String url){
        return retrofitService = new Retrofit.Builder().baseUrl(url)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(ServiceSApi.class);
    }
}
