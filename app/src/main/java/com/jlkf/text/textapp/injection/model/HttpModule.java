package com.jlkf.text.textapp.injection.model;



import com.jlkf.text.textapp.app.BaseApplication;
import com.jlkf.text.textapp.network.Api;
import com.jlkf.text.textapp.network.ApiConstants;
import com.jlkf.text.textapp.network.ApiService;
import com.jlkf.text.textapp.network.RetrofitConfig;
import com.jlkf.text.textapp.network.logger.LoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>
 * Created by 火龙裸 on 2017/8/21.
 */

@Module
public class HttpModule {
    @Provides
    OkHttpClient.Builder provideOkHttpClient() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(BaseApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        LoggingInterceptor logging = new LoggingInterceptor();
        logging.setLevel(LoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }

    @Provides
    Api provideApis(OkHttpClient.Builder builder) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return Api.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.BaseUrl)
                .build().create(ApiService.class));
    }

}
