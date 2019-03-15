package com.jlkf.text.textapp.ui.home.activity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.greendao.gen.UserDao;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.app.BaseApplication;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.ui.home.adapter.GreenDaoAdapter;
import com.jlkf.text.textapp.ui.home.bean.User;
import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.util.decoration.Decoration;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 使用GreenDao数据库操作
 * 所用依赖:implementation 'org.greenrobot:greendao:3.2.2'
 * 依赖位置:https://github.com/greenrobot/greenDAO
 */
public class GreenDaoActivity extends BaseActivity {


    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_sex)
    EditText et_sex;
    @BindView(R.id.et_id_card)
    EditText et_id_card;
    UserDao mUserDao;
    User mUser;

    List<User> users = new ArrayList<>();
    GreenDaoAdapter adapter;

    @Override
    public int intiLayout() {
        return R.layout.activity_green_dao;
    }

    @Override
    public void initView() {
        mUserDao = BaseApplication.getApplication().getDaoSession().getUserDao();

        adapter = new GreenDaoAdapter(users);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.addItemDecoration(Decoration.decoration(10, 10, 20, 20));
        rv_list.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_back, R.id.btn_add, R.id.btn_delete, R.id.btn_select, R.id.btn_update})
    public void onViewClicked(View view) {
        String name = et_name.getText().toString().trim();
        String sex = et_sex.getText().toString().trim();
        String id_card = et_id_card.getText().toString().trim();
        int size=mUserDao.loadAll().size();
        try{
            switch (view.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.btn_add:
                    mUser = new User((long) size, name, sex, id_card);
                    mUserDao.insert(mUser);
                    adapter.replaceData(mUserDao.loadAll());
                    break;
                case R.id.btn_delete:
                    mUserDao.deleteByKey((long) size-1);
                    adapter.replaceData(mUserDao.loadAll());
                    break;
                case R.id.btn_select:
                    adapter.replaceData(mUserDao.loadAll());
                    break;
                case R.id.btn_update:
                    mUser = new User((long) size-1, name, sex, id_card);
                    mUserDao.update(mUser);
                    adapter.replaceData(mUserDao.loadAll());
                    break;
            }
        }catch (SQLiteConstraintException ex){
            toastShort("SQL语句错误了，代码暂未完善，有需要的自己完善一下");
        }

    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_name,R.id.et_sex,R.id.et_id_card};
    }
}
