package com.jlkf.text.textapp.base;



import com.jlkf.text.textapp.network.Api;

import javax.inject.Inject;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:界面无接口操作时调用该类
 */

public class NoPresenter extends BasePresenter<NoContract.View> implements NoContract.Presenter {
    private Api api;

    @Inject
    public NoPresenter(Api api) {
        this.api = api;
    }

}
