package com.jlkf.text.textapp.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.util.errorUtil.Cockroach;
import com.jlkf.text.textapp.util.errorUtil.ExceptionHandler;
import com.jlkf.text.textapp.util.errorUtil.support.CrashLog;
import com.jlkf.text.textapp.util.errorUtil.support.DebugSafeModeUI;

import org.greenrobot.greendao.query.QueryBuilder;


/**
 * Application
 */

public class BaseApplication extends Application {

    private static BaseApplication application;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public BaseApplication() {
    }

    @Override
//  在主线程运行的
    public void onCreate() {
        super.onCreate();
        application = this;
        install();
        LogUtil.init(true);
        setDatabase();
    }

    public static BaseApplication getApplication() {
        return application;
    }

    /**
     * 设置greenDAO
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "user_date", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
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
                LogUtil.e("--->onUncaughtExceptionHappened:" + thread + "<---", throwable);
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
                LogUtil.e("--->onBandageExceptionHappened:" + "<---", throwable);
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
                LogUtil.e("--->onMayBeBlackScreen:" + thread + "<---", e);
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
