package com.jlkf.text.textapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.ActivityMainBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.ui.home.HomeFragment;
import com.jlkf.text.textapp.ui.mine.MineFragment;
import com.jlkf.text.textapp.ui.one.MenuOneFragment;
import com.jlkf.text.textapp.ui.two.MenuTwoFragment;
import com.jlkf.text.textapp.util.DataCleanManager;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;


/**
 * 首页左下角用到的菜单栏 ：依赖来源:【https://github.com/Nightonke/BoomMenu】 对应依赖【implementation 'com.nightonke:boommenu:2.1.1'】
 */
public class MainActivity extends BaseActivity<NoPresenter, ActivityMainBinding> implements NoContract.View {
    HomeFragment homeFragment;
    MenuTwoFragment menuTwoFragment;
    MenuOneFragment menuOneFragment;
    MineFragment mineFragment;

    int number;
    private static String[] text = new String[]{"清除缓存", "菜单二"};
    private static int[] imageResources = new int[]{
            R.mipmap.tab_icon_home_active,
            R.mipmap.tab_icon_home_inactive,
    };
    private static int index = 0;

    private static int imageResourceIndex = 0;


    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);
    }

    @Override
    public void initView() {
        onItemClick(0);
        number = bindingView.boom.getPiecePlaceEnum().pieceNumber();
        for (int i = 0; i < number; i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            if (index == 0) {
                                DataCleanManager.clearAllCache(MainActivity.this);
                                getCacheSize();
                            }
                        }
                    })
                    .normalImageRes(getImageResource())
                    .normalText(getText());
            bindingView.boom.addBuilder(builder);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.fl_all, homeFragment);
        transaction.commit();
        onItemClick(0);
        bindingView.btnBar.rbOne.setOnClickListener(v -> onItemClick(0));
        bindingView.btnBar.rbTwo.setOnClickListener(v -> onItemClick(1));
        bindingView.btnBar.rbTwo.setOnClickListener(v -> onItemClick(2));
        bindingView.btnBar.rbFour.setOnClickListener(v -> onItemClick(3));
    }

    @Override
    public void initIntent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCacheSize();
    }


    /**
     * 选中第几位
     *
     * @param index
     */
    private void onItemClick(int index) {
        changeSelectedTabState(index);
        checkItem(0);
    }

    /**
     * 选中时改变字体颜色
     *
     * @param position 改变第几位
     */
    private void changeSelectedTabState(int position) {
        bindingView.btnBar.rbOne.setTextColor(getResources().getColor(R.color.normal));
        bindingView.btnBar.rbTwo.setTextColor(getResources().getColor(R.color.normal));
        bindingView.btnBar.rbThree.setTextColor(getResources().getColor(R.color.normal));
        bindingView.btnBar.rbFour.setTextColor(getResources().getColor(R.color.normal));
        switch (position) {
            case 0:
                bindingView.btnBar.rbOne.setTextColor(getResources().getColor(R.color.appColor));
                break;
            case 1:
                bindingView.btnBar.rbTwo.setTextColor(getResources().getColor(R.color.appColor));
                break;
            case 2:
                bindingView.btnBar.rbThree.setTextColor(getResources().getColor(R.color.appColor));
                break;
            case 3:
                bindingView.btnBar.rbFour.setTextColor(getResources().getColor(R.color.appColor));
                break;
        }

    }

    /***选中后的方法**/
    public void checkItem(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        changeSelectedTabState(i);
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (menuOneFragment != null) {
            transaction.hide(menuOneFragment);
        }
        if (menuTwoFragment != null) {
            transaction.hide(menuTwoFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_all, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (menuOneFragment == null) {
                    menuOneFragment = new MenuOneFragment();
                    transaction.add(R.id.fl_all, menuOneFragment);
                } else {
                    transaction.show(menuOneFragment);
                }
                break;
            case 2:
                if (menuTwoFragment == null) {
                    menuTwoFragment = new MenuTwoFragment();
                    transaction.add(R.id.fl_all, menuTwoFragment);
                } else {
                    transaction.show(menuTwoFragment);
                }
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_all, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;

        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 圆角菜单里的内容
     *
     * @return
     */
    static String getText() {
        if (index >= text.length) index = 0;
        return text[index++];
    }

    /**
     * 圆角菜单里的图片
     *
     * @return
     */
    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    /**
     * 计算缓存
     */
    public void getCacheSize() {
        try {
            bindingView.tvContent.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
