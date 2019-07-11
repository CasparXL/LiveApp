package com.jlkf.text.textapp.base;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:界面无接口时调用该类
 */

public interface NoContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.BasePresenter<View> {
    }

}
