package com.jlkf.text.textapp.ui.home;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.fragment.BaseFragment;
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

import butterknife.BindView;

/**
 * 首页功能菜单栏
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.rv_menu)
    RecyclerView rv_menu;
    List<String> list;
    HomeAdapter adapter;


    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        listMenu();
        initAdapter();
    }

    @Override
    protected void loadData() {

    }

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
        rv_menu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_menu.addItemDecoration(Decoration.GridDecoration(2, 20, true));
        rv_menu.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_item:
                        onClick(position);
                        break;
                }
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

}
