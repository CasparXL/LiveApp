package com.jlkf.text.textapp.base;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjq.toast.ToastUtils;
import com.jlkf.text.textapp.app.BaseApplication;
import com.jlkf.text.textapp.network.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;


public abstract class BaseFragment<T extends BaseContract.BasePresenter, SV extends ViewDataBinding> extends Fragment implements IBase, BaseContract.BaseView, LifecycleOwner {
    public T mPresenter;
    // 布局view
    protected SV bindingView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (setContentLayout() != 0) {
            initInjector(BaseApplication.getInstance().getApplicationComponent());
            attachView();
            bindingView = DataBindingUtil.inflate(inflater, setContentLayout(), null, false);
            return bindingView.getRoot();
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    public void setLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initIntent();
        initView();
    }

    @Override
    public <T> AutoDisposeConverter<T> bindToLife() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    public void toast(Object text) {
        if (text != null) {
            if (text instanceof String) {
                ToastUtils.show(text);
            } else {
                ToastUtils.show((int) text);
            }
        }
    }
}
