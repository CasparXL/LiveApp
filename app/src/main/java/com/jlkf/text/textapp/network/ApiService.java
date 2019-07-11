package com.jlkf.text.textapp.network;



import com.jlkf.text.textapp.bean.SplashBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:
 */

public interface ApiService {
    @GET("provider/pageData/getStartData")
    Observable<BaseResponse<SplashBean>> getStartData();
}
