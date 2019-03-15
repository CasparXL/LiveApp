package com.jlkf.text.textapp.network;


import com.jlkf.text.textapp.network.error.ExceptionHandle;
import com.jlkf.text.textapp.util.GsonUtil;
import com.jlkf.text.textapp.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 封装Subscriber
 * Created by YoKey.
 */
public abstract class RxSubscriber<T> implements Observer<T> {
    @Override
    public void onError(Throwable e) {
        _onError(ExceptionHandle.handleException(e));
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (d.isDisposed()){
            LogUtil.d("=====================结束======================");
        }else {
            LogUtil.d("=====================开始======================");
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {
        LogUtil.json(GsonUtil.convertVO2String(t));
        _onNext(t);
    }

    abstract void _onNext(T t);

    abstract void _onError(ExceptionHandle.ResponseThrowable e);



}
