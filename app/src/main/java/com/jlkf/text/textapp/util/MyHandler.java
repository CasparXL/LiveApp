package com.jlkf.text.textapp.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * "浪小白" 创建 2019/7/30.
 * 界面名称以及功能: 安全的handler，在安卓调用GC回收机制的时候就可以清除，防止内存泄漏
 */
public class MyHandler extends Handler {
    // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
    private final WeakReference<Activity> mActivity;

    private MyHandler(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        Activity activity = mActivity.get();
        if (activity != null) {
            //这里使用
        }
        super.handleMessage(msg);
    }
}
