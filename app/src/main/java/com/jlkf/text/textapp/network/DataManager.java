package com.jlkf.text.textapp.network;



import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 对接口进行调用
 */
public class DataManager {

    private static void toSubscribe(Observable o, RxSubscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 获取首页轮播图
     *
     * @param userType 类型（1.用户 2.骑手 3.商家）
     * @param type     类型（1.启动页 2.引导页 3.轮播图）
     */
   /* public static void getGuidePage(int userType, int type, RxSubscriber<GuideBean> rxSubscriber) {
        Observable<GuideBean> observable = RetrofitFactory.getInstance().getGuidePage(userType, type);
        toSubscribe(observable, rxSubscriber);
    }*/

}
