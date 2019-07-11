package com.jlkf.text.textapp.base;


import com.uber.autodispose.AutoDisposeConverter;

/**
 * desc:
 * author: wecent .
 * date: 2017/9/2 .
 */
public interface BaseContract {
    interface BasePresenter<T extends BaseView> {
        void attachView(T view);

        void detachView();
    }


    interface BaseView {
        /**
         * 设置加载中
         */
        void setLoading();

        /**
         * 加载完成
         */
        void dismissLoading();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> AutoDisposeConverter<T> bindToLife();


    }
}
