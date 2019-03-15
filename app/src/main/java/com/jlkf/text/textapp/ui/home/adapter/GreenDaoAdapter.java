package com.jlkf.text.textapp.ui.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.ui.home.bean.User;

import java.util.List;

/**
 * 测试数据库内容
 */

public class GreenDaoAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    public GreenDaoAdapter( @Nullable List<User> data) {
        super(R.layout.item_user_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
            helper.setText(R.id.title,"姓名:"+item.getName()+"性别:"+item.getSex()+"身份证号:"+item.getIdCard());
    }
}