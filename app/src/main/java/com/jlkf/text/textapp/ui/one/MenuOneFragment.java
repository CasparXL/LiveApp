package com.jlkf.text.textapp.ui.one;


import android.view.View;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseFragment;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.FragmentMenuOneBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;

public class MenuOneFragment extends BaseFragment<NoPresenter, FragmentMenuOneBinding> implements NoContract.View {


    @Override
    public int setContentLayout() {
        return R.layout.fragment_menu_one;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initIntent() {

    }
}
