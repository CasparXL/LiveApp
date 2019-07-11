package com.jlkf.text.textapp.ui.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.bean.dao.User;
import com.jlkf.text.textapp.util.UIUtil;

import java.util.List;

/**
 * 测试数据库内容
 */

public class GreenDaoAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    public GreenDaoAdapter(@Nullable List<User> data) {
        super(R.layout.item_user_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.title, "姓名:" + UIUtil.isNullString(item.getName()) + "性别:" + UIUtil.isNullString(item.getSex()) + "身份证号:" + UIUtil.isNullString(item.getIdCard()));
    }
}