package com.jlkf.text.textapp.ui.two;



import android.view.View;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseFragment;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.FragmentMenuTwoBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;

public class MenuTwoFragment extends BaseFragment<NoPresenter, FragmentMenuTwoBinding> implements NoContract.View {

    @Override
    public int setContentLayout() {
        return R.layout.fragment_menu_two;
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
