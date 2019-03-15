package com.jlkf.text.textapp.network;



import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


/**
 * 接口对应地址
 */
public interface ServiceSApi {

    /**
     * 分类对应OE配件
     *
     * @param categoryId 三级分类id
     * @param mikey      车型ID（在车型信息获取）
     * @return
     */
    @GET("onme/getOeNumber")
    Observable<Object> getOeNumber(@Query("categoryId") String categoryId, @Query("mikey") String mikey);

}
