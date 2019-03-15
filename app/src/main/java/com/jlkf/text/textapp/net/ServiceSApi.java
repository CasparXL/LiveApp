package com.jlkf.text.textapp.net;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * 网络请求的接口
 */
public interface ServiceSApi {

    /**********************************************************GET请求*************************************/
    @GET("provider/pageData/getStartData")
    Observable<BaseResponse<BaseStringResponse>> getStartData();



    /**********************************************************POST请求*************************************/

    @POST("provider/movie/haveSee")
    Observable<BaseStringResponse> haveSee(@Header("Authorization") String header, @Body RequestBody address);
}
