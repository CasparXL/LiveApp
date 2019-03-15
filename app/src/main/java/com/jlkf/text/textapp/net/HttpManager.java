package com.jlkf.text.textapp.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jlkf.text.textapp.net.util.logger.Logger;
import com.jlkf.text.textapp.net.util.logger.LoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 */
public class HttpManager {
    private volatile static HttpManager httpClient;
    private final ServiceSApi apiInterface;  //接口对象


    //同步锁，单例模式，保证多线程访问的安全
    public static synchronized HttpManager getInstance() {
        if (httpClient == null) {
            synchronized (HttpManager.class) {
                if (httpClient == null) {
                    httpClient = new HttpManager();
                }
            }
        }
        return httpClient;
    }

    /*
    * 构造方法
    * */
    private HttpManager() {
        LoggingInterceptor logging = new LoggingInterceptor(new Logger());
        logging.setLevel(LoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS) //连接超时时间 单位:秒
                .readTimeout(8, TimeUnit.SECONDS) //读取超时时间 单位:秒
                .writeTimeout(8, TimeUnit.SECONDS) //写超时时间 单位:秒
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request;
                        request = original.newBuilder()
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                        //.header("uId", 5+ "")
                    }
                })
                .build();

        Gson gson = new GsonBuilder().serializeNulls().setLenient().create();  //setLenient()严格按照日期的方式解析json
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://xxx.xxx.x.xx:xxxx/")
                .addConverterFactory(GsonConverterFactory.create(gson))   //配置转化库 默认是 json
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiInterface = retrofit.create(ServiceSApi.class);//创建接口的实例

    }

    private Observable handle(Observable observable) {
        return observable.unsubscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    /**************************网络请求***************************************/

    /**
     *
     */
    public void getStartData(RxSubscriber<BaseResponse> rxSubscriber) {
        handle(apiInterface.getStartData()).subscribe(rxSubscriber);
    }

}
