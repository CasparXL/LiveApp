package com.jlkf.text.textapp.injection.component;




import com.jlkf.text.textapp.MainActivity;
import com.jlkf.text.textapp.ui.home.HomeFragment;
import com.jlkf.text.textapp.ui.home.activity.GreenDaoActivity;
import com.jlkf.text.textapp.ui.home.activity.MovieActivity;
import com.jlkf.text.textapp.ui.home.activity.PhoneActivity;
import com.jlkf.text.textapp.ui.home.activity.PhotoActivity;
import com.jlkf.text.textapp.ui.home.activity.StatisticalActivity;
import com.jlkf.text.textapp.ui.home.activity.SuperCalendarActivity;
import com.jlkf.text.textapp.ui.mine.MineFragment;
import com.jlkf.text.textapp.ui.one.MenuOneFragment;
import com.jlkf.text.textapp.ui.two.MenuTwoFragment;

import dagger.Component;

/**
 * Http依赖注入 每创建一个新的activity，加入一个方法，不然会崩溃
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {
    void inject(MainActivity childNodeActivity);
    void inject(HomeFragment childNodeActivity);

    void inject(MenuOneFragment menuOneFragment);

    void inject(MenuTwoFragment menuTwoFragment);

    void inject(MineFragment mineFragment);

    void inject(GreenDaoActivity greenDaoActivity);

    void inject(MovieActivity movieActivity);

    void inject(PhoneActivity phoneActivity);

    void inject(PhotoActivity photoActivity);

    void inject(StatisticalActivity statisticalActivity);

    void inject(SuperCalendarActivity superCalendarActivity);
}
