package com.jlkf.text.textapp.ui.home.activity;


import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jlkf.text.textapp.R;

import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.bean.dao.User;
import com.jlkf.text.textapp.bean.dao.UserDao;
import com.jlkf.text.textapp.databinding.ActivityGreenDaoBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.ui.home.adapter.GreenDaoAdapter;
import com.jlkf.text.textapp.util.decoration.Decoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 原：使用GreenDao数据库操作 现在：  更换数据库同一个团队的作品 [ObjectBox]，由原本的db文件改为mdb
 * 所用依赖:原来：implementation 'org.greenrobot:greendao:3.2.2' 现在：releaseImplementation io.objectbox:objectbox-android:2.3.4
 * 依赖位置:原来：https://github.com/greenrobot/greenDAO 现在:https://github.com/objectbox/objectbox-java
 */
public class GreenDaoActivity extends BaseActivity<NoPresenter, ActivityGreenDaoBinding> implements NoContract.View {
    GreenDaoAdapter adapter;
    List<User> users;
    public static final ObservableField<String> name = new ObservableField<>();

    public static final ObservableField<String> sex = new ObservableField<>();

    public static final ObservableField<String> idCard = new ObservableField<>();

    @Override
    public int setContentLayout() {
        return R.layout.activity_green_dao;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);
    }

    @Override
    public void initView() {
        bindingView.include2.tvTitle.setText("数据库");
        users = new ArrayList<>();//不能直接==UserDao.getAll();否则在
        users.addAll(UserDao.getAll());
        adapter = new GreenDaoAdapter(users);
        bindingView.rvList.setLayoutManager(new LinearLayoutManager(this));
        bindingView.rvList.addItemDecoration(Decoration.decoration(10, 10, 20, 20));
        bindingView.rvList.setAdapter(adapter);
        bindingView.btnDelete.setOnClickListener(v -> {
            if (UserDao.getCount() == 0) {
                toast("已经没有数据了");
                return;
            }
            UserDao.delete(UserDao.getAll().get(0).getId());
            users.clear();
            if (UserDao.getCount() > 0){
                List<User> lists=new ArrayList<>(UserDao.getAll());
                users.addAll(lists);
            }
            adapter.notifyDataSetChanged();
        });

        bindingView.btnAdd.setOnClickListener(v -> {
            if (name.get() == null || sex.get() == null || idCard == null) {
                toast("请至少都输入一点~");
                return;
            }
            UserDao.insertOrUpdateBlogItem(new User(name.get() == null ? "" : name.get(), sex.get() == null ? "" : sex.get(), idCard.get() == null ? "" : idCard.get()));
            users.clear();
            if (UserDao.getCount() > 0){
                List<User> lists=new ArrayList<>(UserDao.getAll());
                users.addAll(lists);
            }
            adapter.notifyDataSetChanged();
        });
        bindingView.btnSelect.setOnClickListener(v -> {
            users.clear();
            if (UserDao.getCount() > 0){
                List<User> lists=new ArrayList<>(UserDao.getAll());
                users.addAll(lists);
            }
            adapter.notifyDataSetChanged();
        });
        bindingView.btnUpdate.setOnClickListener(v -> {
            if (UserDao.getAll().size() == 0) {
                toast("已经没有数据了");
                return;
            }
            UserDao.insertOrUpdateBlogItem(new User(UserDao.getAll().get(0).getId(), name.get() == null ? "" : name.get(), sex.get() == null ? "" : sex.get(), idCard.get() == null ? "" : idCard.get()));
            users.clear();
            if (UserDao.getCount() > 0){
                List<User> lists=new ArrayList<>(UserDao.getAll());
                users.addAll(lists);
            }
            adapter.notifyDataSetChanged();
        });
        bindingView.include2.ivBack.setOnClickListener(v -> finish());

    }

    @Override
    public void initIntent() {

    }


    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_name, R.id.et_sex, R.id.et_id_card};
    }
}
