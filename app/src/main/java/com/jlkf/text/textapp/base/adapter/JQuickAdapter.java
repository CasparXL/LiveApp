package com.jlkf.text.textapp.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jlkf.text.textapp.R;

import java.util.List;

/**
 * 自定义适配器
 *
 * @param <T> 实体类
 * @param <B> ViewBinding
 */
public abstract class JQuickAdapter<T, B extends ViewDataBinding> extends BaseQuickAdapter<T, JViewHolder<B>> {
    private B binding;

    public JQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    public JQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public JQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    /*  @Override
      protected void convert(BaseViewHolder helper, T item) {
          binding = DataBindingUtil.bind(helper.itemView);//使用绑定方法，原本是使用自定义Holder
          convert(helper, item, binding);

      }*/

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        if (layoutResId != mLayoutResId) {
            return super.getItemView(layoutResId, parent);
        }
        binding = DataBindingUtil.inflate(
                mLayoutInflater,
                layoutResId,
                parent,
                false);
        onBinding(binding);
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }
    /**
     * 绑定RecyclerView
     *
     */
    public void bindToRecyclerView(RecyclerView recyclerView) {
        super.bindToRecyclerView(recyclerView);
    }
    /**
     * 已初始化Binding
     */
    protected abstract void onBinding(B binding);


}
