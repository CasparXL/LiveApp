package com.jlkf.text.textapp.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.jlkf.text.textapp.app.BaseApplication;
import com.jlkf.text.textapp.network.RxLifecycleUtils;
import com.jlkf.text.textapp.util.KeyBoardUtils;
import com.uber.autodispose.AutoDisposeConverter;

import javax.inject.Inject;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter, SV extends ViewDataBinding> extends AppCompatActivity implements IBase, BaseContract.BaseView {
    // 布局view
    protected SV bindingView;
    //P层
    @Inject
    public T mPresenter;
    //沉浸式
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true);
        mImmersionBar.init();
        initInjector(BaseApplication.getInstance().getApplicationComponent());
        attachView();
        //创建一个新的的布局绑定
        bindingView = DataBindingUtil.setContentView(this, setContentLayout());
        initIntent();
        initView();
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
    public <T> AutoDisposeConverter<T> bindToLife() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
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

    //--------------------------隐藏软键盘
    @Override
    public void finish() {
        super.finish();
        hideSoftKeyboard();
    }

    protected void hideSoftKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputManger = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            assert inputManger != null;
            inputManger.hideSoftInputFromWindow(view.getWindowToken(),
                    0);
        }
    }

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    break;
                }
            }
        }


    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(int[] ids, MotionEvent ev) {
        int[] location = new int[2];
        for (int id : ids) {
            View view = findViewById(id);
            if (view == null) continue;
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region 右滑返回上级


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev)) return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                if (isTouchView(hideSoftByEditViewIds(), ev))
                    return super.dispatchTouchEvent(ev);
                //隐藏键盘
                KeyBoardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }
}
