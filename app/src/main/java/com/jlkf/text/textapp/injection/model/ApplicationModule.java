package com.jlkf.text.textapp.injection.model;

import android.content.Context;


import com.jlkf.text.textapp.app.BaseApplication;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    BaseApplication provideApplication() {
        return (BaseApplication) mContext.getApplicationContext();
    }
    @Provides
    Context provideContext() {
        return mContext;
    }
}
