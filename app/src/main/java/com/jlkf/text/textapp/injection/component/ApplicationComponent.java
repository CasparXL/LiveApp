package com.jlkf.text.textapp.injection.component;

import android.content.Context;


import com.jlkf.text.textapp.app.BaseApplication;
import com.jlkf.text.textapp.injection.model.ApplicationModule;
import com.jlkf.text.textapp.injection.model.HttpModule;
import com.jlkf.text.textapp.network.Api;

import dagger.Component;

/**
 * Http依赖注入 每创建一个新的activity，加入一个方法，不然会崩溃
 */
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {

    BaseApplication getApplication();

    Context getContext();

    Api getApi();

}
