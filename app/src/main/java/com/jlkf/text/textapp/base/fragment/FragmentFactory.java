package com.jlkf.text.textapp.base.fragment;

import android.util.Log;

import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.ui.home.HomeFragment;
import com.jlkf.text.textapp.ui.one.MenuOneFragment;
import com.jlkf.text.textapp.ui.two.MenuTwoFragment;
import com.jlkf.text.textapp.ui.mine.MineFragment;

import java.util.HashMap;


public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> mCaches = new HashMap<>();

    private static FragmentFactory sInstace;


    private FragmentFactory() {
    }


    public static FragmentFactory getInstace() {
        if (sInstace == null) {
            sInstace = new FragmentFactory();
        }
        return sInstace;
    }


    public BaseFragment getFragment(int position) {
        //使用集合把每个position对应的fragment记录下来，也就是存起来，内存缓存
        BaseFragment fragment = null;
        //A.取缓存，非第一次
        fragment = mCaches.get(position);
        if (fragment != null) {
            Log.e(getClass().getSimpleName(), "取到了fragment缓存");
            return fragment;
        }

        //B.存缓存，第一次
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                BaseActivity.isFirstCreated = true;//至少有两条保证添加Fragment不会报错
                break;
            case 1:
                fragment = new MenuOneFragment();
                BaseActivity.isFirstCreated = true;
                break;
            case 2:
                fragment = new MenuTwoFragment();
                break;
            case 3:
                fragment = new MineFragment();
                break;
            default:
                break;
        }
        mCaches.put(position, fragment);
        Log.e(getClass().getSimpleName(), "将Fragment存入缓存");
        return fragment;
    }


    public void clear() {
        mCaches.clear();
    }
}
