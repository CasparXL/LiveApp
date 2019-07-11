package com.jlkf.text.textapp.injection.model;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule
{
    private Activity mActivity;

    public ActivityModule(Activity mActivity)
    {
        this.mActivity = mActivity;
    }

    @Provides
    Activity provideActivity()
    {
        return mActivity;
    }

    @Provides
    Context provideContext()
    {
        return mActivity;
    }
}
