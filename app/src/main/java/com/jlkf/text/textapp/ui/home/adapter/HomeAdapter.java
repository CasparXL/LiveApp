package com.jlkf.text.textapp.ui.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jlkf.text.textapp.R;

import java.util.List;

/**
 * 首页的适配器
 */
public class HomeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.btn_item);
        helper.setText(R.id.btn_item,item);
    }
}
