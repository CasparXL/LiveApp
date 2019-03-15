package com.jlkf.text.textapp.net;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;


import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.util.WeiboDialogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * 封装Subscriber
 * Created by YoKey.
 */
public abstract class RxSubscriber<T> implements Observer<T> {
    private Activity context;
    private Dialog mDialog;
    private boolean isProgress;
    private String load;
    public RxSubscriber(Activity context, boolean isProgress, String load) {
        this.context=context;
        this.isProgress = isProgress;
        this.load = load;
    }

    protected RxSubscriber(Activity context) {
        this.context=context;
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (context!=null){

            if (!d.isDisposed()) {
                if (isProgress) {
                    if (load != null)
                        mDialog = WeiboDialogUtils.createLoadingDialog(context, load);
                    else
                        mDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中...");

                    // mDialog = DialogThridUtils.showWaitDialog(this, "加载中...", false, true);
                }
            }
        }

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {
        if (context!=null) {

            if (isProgress && mDialog != null && mDialog.isShowing()) {
                WeiboDialogUtils.closeDialog(mDialog);
            }

            if (t instanceof BaseResponse) {
                BaseResponse response = (BaseResponse) t;
                //网络请求正确码
                if (response.getCode() == 0) {
                    if (response.getData() == null) {
                        try {
                            _onNext((T) response);
                        } catch (ClassCastException e) {
                            _onNext((T) response.getData());
                        }
                    } else
                        _onNext((T) response.getData());
                } else {
                    _onError(new ExceptionHandle.ResponseThrowable(response.getMsg(), response.getCode()));
                }
            } else {
                _onNext(t);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (context!=null) {

            if (isProgress && mDialog != null && mDialog.isShowing()) {
                WeiboDialogUtils.closeDialog(mDialog);
            }
            _onError(ExceptionHandle.handleException(e));
        }
        LogUtil.e("http : " + ExceptionHandle.handleException(e).message);

    }

    public abstract void _onNext(T t);

    protected abstract void _onError(ExceptionHandle.ResponseThrowable e);

}
