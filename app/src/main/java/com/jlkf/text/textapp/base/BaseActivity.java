package com.jlkf.text.textapp.base;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.app.BaseApplication;
import com.jlkf.text.textapp.base.fragment.BaseFragment;
import com.jlkf.text.textapp.util.AppManager;
import com.jlkf.text.textapp.util.KeyBoardUtils;
import com.jlkf.text.textapp.util.LogUtil;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    /***封装toast对象**/
    private static Toast toast;
    /***沉浸式**/
    private ImmersionBar mImmersionBar;
    /***是否第一次创建**/
    public static Boolean isFirstCreated = false;
    /***碎片管理器**/
    private FragmentManager mFragmentManager;
    /***临时碎片**/
    private Fragment showFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true).keyboardEnable(true);
        mImmersionBar.init();
        AppManager.getAppManager().addActivity(this);
        mFragmentManager = getSupportFragmentManager();
        //设置布局
        setContentView(intiLayout());
        //绑定控件需要在setContentView前初始化后
        ButterKnife.bind(this);
        //初始化控件
        initView();
        //初始化监听器
        initListener();
        //设置数据
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("类名===" + this.getClass().getName());
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 初始化监听器
     */
    public abstract void initListener();

    /**
     * 设置数据
     */
    public abstract void initData();

    /**
     * 显示长toast
     *
     * @param msg 内容
     */
    @SuppressLint("ShowToast")
    public void toastLong(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();//设置新的消息提示
    }

    /**
     * 显示短toast
     *
     * @param msg 内容
     */
    @SuppressLint("ShowToast")
    public void toastShort(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();//设置新的消息提示
    }


    public void fragmentManager(int fragId, BaseFragment fragment, String tag) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (!fragment.isAdded() && null == getSupportFragmentManager().findFragmentByTag(tag)
                && isFirstCreated) {
            if (showFragment != null) {
                ft.hide(showFragment).add(fragId, fragment, tag);
            } else {
                ft.add(fragId, fragment, tag);
            }
        } else { //已经加载进容器里去了....
            if (showFragment != null) {
                ft.hide(showFragment).show(fragment);
            } else {
                ft.show(fragment);
            }
        }
        showFragment = fragment;
        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    //--------------------------隐藏软键盘

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        AppManager.getAppManager().finishActivity(this);
    }
}
