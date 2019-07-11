package com.jlkf.text.textapp.network;



import com.jlkf.text.textapp.bean.SplashBean;

import io.reactivex.Observable;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:
 */

public class Api {
    public static Api mInstance;

    private ApiService mService;

    public Api(ApiService weiXunApiService) {
        this.mService = weiXunApiService;
    }

    /* public static Api getInstance(ApiService weiXunApiService) {
         if (mInstance == null)
             mInstance = new Api(weiXunApiService);
         return mInstance;
     }*/

    //同步锁，单例模式，保证多线程访问的安全
    public static synchronized Api getInstance(ApiService weiXunApiService) {
        if (mInstance == null) {
            synchronized (Api.class) {
                if (mInstance == null) {
                    mInstance = new Api(weiXunApiService);
                }
            }
        }
        return mInstance;
    }

    public Observable<BaseResponse<SplashBean>> getStartData() {
        return mService.getStartData();
    }
}
