package com.jlkf.text.textapp.base.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jlkf.text.textapp.R;

public class JViewHolder <B extends ViewDataBinding> extends BaseViewHolder {
    public final B binding;

    public JViewHolder(View view) {
        super(view);
        this.binding = (B) view.getTag(R.id.BaseQuickAdapter_databinding_support);
    }

}
