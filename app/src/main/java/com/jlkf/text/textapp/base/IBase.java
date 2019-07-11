package com.jlkf.text.textapp.base;


import com.jlkf.text.textapp.injection.component.ApplicationComponent;

public interface IBase {

    int setContentLayout();

    void initInjector(ApplicationComponent appComponent);

    void initView();

    void initIntent();
}
