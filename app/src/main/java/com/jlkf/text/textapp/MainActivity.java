package com.jlkf.text.textapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.fragment.BaseFragment;
import com.jlkf.text.textapp.base.fragment.FragmentFactory;
import com.jlkf.text.textapp.util.DataCleanManager;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页左下角用到的菜单栏 ：依赖来源:【https://github.com/Nightonke/BoomMenu】 对应依赖【implementation 'com.nightonke:boommenu:2.1.1'】
 *
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rb_one)
    RadioButton rb_one;
    @BindView(R.id.rb_two)
    RadioButton rb_two;
    @BindView(R.id.rb_three)
    RadioButton rb_three;
    @BindView(R.id.rb_four)
    RadioButton rb_four;
    @BindView(R.id.boom)
    BoomMenuButton boomMenuButton;
    @BindView(R.id.tv_content)
    TextView tv_content;
    int number;
    private static String[] text = new String[]{"清除缓存", "菜单二"};
    private static int[] imageResources = new int[]{
            R.mipmap.tab_icon_home_active,
            R.mipmap.tab_icon_home_inactive,
    };
    private static int index = 0;

    private static int imageResourceIndex = 0;

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        onItemClick(0);
        number = boomMenuButton.getPiecePlaceEnum().pieceNumber();
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
            boomMenuButton.addBuilder(builder);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCacheSize();
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    /**
     * 选中第几位
     *
     * @param index
     */
    private void onItemClick(int index) {
        BaseFragment fragment = FragmentFactory.getInstace().getFragment(index);
        fragmentManager(R.id.fl_all, fragment, "" + index);
        changeSelectedTabState(index);
    }

    /**
     * 选中时改变字体颜色
     *
     * @param position 改变第几位
     */
    private void changeSelectedTabState(int position) {
        rb_one.setTextColor(getResources().getColor(R.color.normal));
        rb_two.setTextColor(getResources().getColor(R.color.normal));
        rb_three.setTextColor(getResources().getColor(R.color.normal));
        rb_four.setTextColor(getResources().getColor(R.color.normal));
        switch (position) {
            case 0:
                rb_one.setTextColor(getResources().getColor(R.color.appColor));
                break;
            case 1:
                rb_two.setTextColor(getResources().getColor(R.color.appColor));
                break;
            case 2:
                rb_three.setTextColor(getResources().getColor(R.color.appColor));
                break;
            case 3:
                rb_four.setTextColor(getResources().getColor(R.color.appColor));
                break;
        }
    }

    /**
     * 底部栏切换
     */
    @OnClick({R.id.rb_one, R.id.rb_two, R.id.rb_three, R.id.rb_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_one:
                onItemClick(0);
                break;
            case R.id.rb_two:
                onItemClick(1);
                break;
            case R.id.rb_three:
                onItemClick(2);
                break;
            case R.id.rb_four:
                onItemClick(3);
                break;
        }
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
            tv_content.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
