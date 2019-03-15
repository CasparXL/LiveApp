package com.jlkf.text.textapp.ui.home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.ui.home.bean.ItemData;

import java.util.List;

/**
 * 功能二
 */
public class PhoneAdapter extends BaseQuickAdapter<ItemData, BaseViewHolder> {
    public PhoneAdapter(@Nullable List<ItemData> data) {
        super(R.layout.item_phone, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemData item) {
        helper.addOnClickListener(R.id.title);
       // Glide.with(mContext).load(item).apply(new RequestOptions().error(R.mipmap.ic_launcher)).into((ImageView) helper.getView(R.id.iv_picture));
        helper.setText(R.id.title,item.getTitle());
    }
}
