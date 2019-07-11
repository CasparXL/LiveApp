package com.jlkf.text.textapp.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;
import com.jlkf.text.textapp.BuildConfig;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.bean.dao.MyObjectBox;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerApplicationComponent;
import com.jlkf.text.textapp.injection.model.ApplicationModule;
import com.jlkf.text.textapp.injection.model.HttpModule;
import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.util.SP;
import com.jlkf.text.textapp.util.errorUtil.Cockroach;
import com.jlkf.text.textapp.util.errorUtil.ExceptionHandler;
import com.jlkf.text.textapp.util.errorUtil.support.CrashLog;
import com.jlkf.text.textapp.util.errorUtil.support.DebugSafeModeUI;
import com.tencent.bugly.crashreport.CrashReport;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;


/**
 * Application
 */

public class BaseApplication extends Application {

    private static Context mContext;//上下文

    private ApplicationComponent mApplicationComponent;

    private static BaseApplication application;
    private BoxStore boxStore;

    public BaseApplication() {
    }

    @Override
//  在主线程运行的
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        application = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        SP.init(this);
        ToastUtils.init(this);
        LogUtil.init(true, "浪小白");
        boxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(this);
            LogUtil.e("Started: " + started);
        }
        install();
        CrashReport.initCrashReport(getApplicationContext(), "3b5120f43f", true); //bugly集成
    }

    public static BaseApplication getInstance() {
        return application;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getContext() {
        return mContext;
    }

    public static BaseApplication getApplication() {
        return application;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    /**
     * 报错
     */
    private void install() {
        final Thread.UncaughtExceptionHandler sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler();
        final Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        DebugSafeModeUI.init(this);
        Cockroach.install(new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                LogUtil.e("--->onUncaughtExceptionHappened:" + thread + "<---" + throwable);
                CrashLog.saveCrashLog(getApplicationContext(), throwable);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        toast.setText(R.string.safe_mode_excep_tips);
                        toast.show();
                    }
                });
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
                LogUtil.e("--->onBandageExceptionHappened:" + "<---" + throwable);
                toast.setText("Cockroach Worked");
                toast.show();
            }

            @Override
            protected void onEnterSafeMode() {
                int tips = R.string.safe_mode_tips;
                toast.setText(getResources().getString(tips));
                DebugSafeModeUI.showSafeModeUI();
            }

            @Override
            protected void onMayBeBlackScreen(Throwable e) {
                final Thread thread = Looper.getMainLooper().getThread();
                LogUtil.e("--->onMayBeBlackScreen:" + thread + "<---" + e);
                //黑屏时建议直接杀死app 也可以取消这句话，但之后未加载的界面将不会再进行加载
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));
                    }
                }, 2000);
            }
        });
    }

}
