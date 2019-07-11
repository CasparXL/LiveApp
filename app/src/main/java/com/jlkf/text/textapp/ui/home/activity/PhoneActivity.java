package com.jlkf.text.textapp.ui.home.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.ActivityPhoneBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.ui.home.adapter.PhoneAdapter;
import com.jlkf.text.textapp.ui.home.bean.ItemData;
import com.jlkf.text.textapp.util.index.decoration.DivideItemDecoration;
import com.jlkf.text.textapp.util.index.decoration.GroupHeaderItemDecoration;
import com.jlkf.text.textapp.util.index.helper.SortHelper;
import com.jlkf.text.textapp.util.index.listener.OnSideBarTouchListener;
import com.jlkf.text.textapp.util.index.weiget.SideBar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能，索引列表[经典例子：城市列表]
 * 依赖来源:https://github.com/SheHuan/GroupIndexLib
 * 所需依赖：implementation 'com.github.promeg:tinypinyin:2.0.3'
 */
public class PhoneActivity extends BaseActivity<NoPresenter, ActivityPhoneBinding> implements NoContract.View {


    List<ItemData> mList = new ArrayList<>();
    PhoneAdapter adapter;
    List<String> tags;
    LinearLayoutManager layoutManager;


    @Override
    public int setContentLayout() {
        return R.layout.activity_phone;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);

    }

    @Override
    public void initView() {
        bindingView.include.tvTitle.setText("城市列表");
        mList.add(new ItemData("北京"));
        mList.add(new ItemData("上海"));
        mList.add(new ItemData("广州"));
        mList.add(new ItemData("深圳"));
        mList.add(new ItemData("西安"));
        mList.add(new ItemData("成都"));
        mList.add(new ItemData("南京"));
        mList.add(new ItemData("三亚"));
        mList.add(new ItemData("开封"));
        mList.add(new ItemData("杭州"));
        mList.add(new ItemData("嘉兴"));
        mList.add(new ItemData("兰州"));
        mList.add(new ItemData("新疆"));
        mList.add(new ItemData("西藏"));
        mList.add(new ItemData("昆明"));
        mList.add(new ItemData("大理"));
        mList.add(new ItemData("桂林"));
        mList.add(new ItemData("东莞"));
        mList.add(new ItemData("台湾"));
        mList.add(new ItemData("香港"));
        mList.add(new ItemData("澳门"));
        mList.add(new ItemData("宝鸡"));
        mList.add(new ItemData("蚌埠"));
        mList.add(new ItemData("钓鱼岛"));
        mList.add(new ItemData("安康"));
        mList.add(new ItemData("苏州"));
        mList.add(new ItemData("青岛"));
        mList.add(new ItemData("郑州"));
        mList.add(new ItemData("洛阳"));
        mList.add(new ItemData("石家庄"));
        mList.add(new ItemData("乌鲁木齐"));
        mList.add(new ItemData("武汉"));
        mList.add(new ItemData("←_←"));
        mList.add(new ItemData("⊙﹏⊙"));
        mList.add(new ItemData("Hello China"));
        mList.add(new ItemData("宁波"));
        initAdapter();
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.title) {
                toast(mList.get(position).getTitle());
            }
        });
        bindingView.sbRight.setOnSideBarTouchListener(tags, new OnSideBarTouchListener() {
            @Override
            public void onTouch(String text, int position) {
                bindingView.tip.setVisibility(View.VISIBLE);
                bindingView.tip.setText(text);
                /*if ("#".equals(text)) {
                    layoutManager.scrollToPositionWithOffset(0, 0);
                    return;
                }*/
                if (position != -1) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                }
            }

            @Override
            public void onTouchEnd() {
                bindingView.tip.setVisibility(View.GONE);
            }
        });
        bindingView.include.ivBack.setOnClickListener(v -> finish());
    }

    @Override
    public void initIntent() {

    }


    public void initAdapter() {
        SortHelper<ItemData> sortHelper = new SortHelper<ItemData>() {
            @Override
            public String sortField(ItemData data) {
                return data.getTitle();
            }
        };
        sortHelper.sortByLetter(mList);
        tags = sortHelper.getTags(mList);
        adapter = new PhoneAdapter(mList);
        layoutManager = new LinearLayoutManager(this);
        bindingView.rvList.setLayoutManager(layoutManager);
        bindingView. rvList.addItemDecoration(new GroupHeaderItemDecoration(this).setTags(tags).setGroupHeaderHeight(30)
                .setGroupHeaderLeftPadding(20));
        bindingView.rvList.addItemDecoration(new DivideItemDecoration().setTags(tags));
        bindingView.rvList.setAdapter(adapter);
    }

}
