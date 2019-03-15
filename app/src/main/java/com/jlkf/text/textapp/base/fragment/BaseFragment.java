package com.jlkf.text.textapp.base.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;



import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nie.xin on 2017/5/31.
 */

public abstract class BaseFragment extends Fragment implements FragmentBackHandler {
    private static final String TAG = "BaseFragment";
    Unbinder unbinder;
    private FragmentManager mFragmentManager;
    private static Toast toast;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
        ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mFragmentManager = getChildFragmentManager();
        return view;
    }

    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    @Override
    public final boolean onBackPressed() {
        return interceptBackPressed()
            || (getBackHandleViewPager() == null
                ? BackHandlerHelper.handleBackPress(this)
                : BackHandlerHelper.handleBackPress(getBackHandleViewPager()));
    }


    public boolean interceptBackPressed() {
        return false;
    }


    /**
     * 2.1 版本已经不在需要单独对ViewPager处理
     *
     * @deprecated
     */
    @Deprecated
    public ViewPager getBackHandleViewPager() {
        return null;
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
    /**
     * 设置fragment需要展示的布局ID
     */
    protected abstract int getContentView();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        loadData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void toast(String str) {
        if (getActivity()!=null&&!getActivity().isFinishing()) {
            if (toast==null){
                toast = Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT);
            }else {
                toast.setText(str);
            }
            toast.show();//设置新的消息提示

        }
    }

    /**
     * 初始化
     */
    protected abstract void initView(View view);

    protected abstract void loadData();


    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @SuppressLint("NewApi")
    protected void hideSoftKeyboard() {
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputManger = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
            assert inputManger != null;
            inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }




    @Override
    public void onResume() {
        super.onResume();
        Log.d("===", "类名===" + this.getClass().getName());
    }

}
