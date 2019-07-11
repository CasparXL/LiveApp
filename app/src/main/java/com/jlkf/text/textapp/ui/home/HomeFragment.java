package com.jlkf.text.textapp.ui.home;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseFragment;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.FragmentHomeBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.ui.home.activity.AudioPlayActivity;
import com.jlkf.text.textapp.ui.home.activity.GreenDaoActivity;
import com.jlkf.text.textapp.ui.home.activity.MovieActivity;
import com.jlkf.text.textapp.ui.home.activity.PhoneActivity;
import com.jlkf.text.textapp.ui.home.activity.PhotoActivity;
import com.jlkf.text.textapp.ui.home.activity.StatisticalActivity;
import com.jlkf.text.textapp.ui.home.activity.SuperCalendarActivity;
import com.jlkf.text.textapp.ui.home.adapter.HomeAdapter;
import com.jlkf.text.textapp.util.decoration.Decoration;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页功能菜单栏
 */
public class HomeFragment extends BaseFragment<NoPresenter, FragmentHomeBinding> implements NoContract.View {

    List<String> list;
    HomeAdapter adapter;


    /**
     * 功能列表
     */
    public void listMenu() {
        list.add("拍照框架");
        list.add("索引列表");
        list.add("视频以及直播");
        list.add("图表");
        list.add("日历");
        list.add("影院选座");
        list.add("数据库");
    }

    /**
     *
     */
    public void initAdapter() {
        adapter = new HomeAdapter(list);
        bindingView.rvMenu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        bindingView.rvMenu.addItemDecoration(Decoration.GridDecoration(2, 20, true));
        bindingView.rvMenu.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.btn_item:
                    onClick(position);
                    break;
            }
        });
    }

    public void onClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), PhotoActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), AudioPlayActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), StatisticalActivity.class));
                break;
            case 4:
                startActivity(new Intent(getActivity(), SuperCalendarActivity.class));
                break;
            case 5:
                startActivity(new Intent(getActivity(), MovieActivity.class));
                break;
            case 6:
                startActivity(new Intent(getActivity(), GreenDaoActivity.class));
                break;
        }
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        listMenu();
        initAdapter();
    }

    @Override
    public void initIntent() {

    }
}
