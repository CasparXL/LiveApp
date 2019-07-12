package com.jlkf.text.textapp.ui.dialog;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.dialog.BaseDialog;
import com.jlkf.text.textapp.base.dialog.BaseDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/12/2
 * desc   : 菜单选择框
 */
public final class MenuDialog {

    public static final class Builder
            extends BaseDialogFragment.Builder<Builder>
            implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

        private OnListener mListener;
        private boolean mAutoDismiss = true;
        List<String> mList;
        private RecyclerView mRecyclerView;
        private MenuAdapter mAdapter;
        private TextView mCancelView;

        public Builder(FragmentActivity activity) {
            super(activity);

            setContentView(R.layout.dialog_menu);
            setAnimStyle(BaseDialog.AnimStyle.SCALE);
            setGravity(Gravity.BOTTOM);
            setWidth(MATCH_PARENT);

            mRecyclerView = findViewById(R.id.rv_dialog_menu_list);
            mCancelView = findViewById(R.id.tv_dialog_menu_cancel);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            if (mList==null){
                mList=new ArrayList<>();
            }
            mAdapter = new MenuAdapter(mList);
            mAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mAdapter);

            mCancelView.setOnClickListener(this);
        }

        public Builder setList(int... resIds) {
            List<String> data = new ArrayList<>(resIds.length);
            for (int resId : resIds) {
                data.add(getString(resId));
            }
            return setList(data);
        }

        public Builder setList(String... data) {
            return setList(Arrays.asList(data));
        }

        public Builder setList(List<String> data) {
            mList.clear();
            mList.addAll(data);
            mAdapter.notifyDataSetChanged();
            return this;
        }

        public Builder setCancel(int resId) {
            return setCancel(getText(resId));
        }

        public Builder setCancel(CharSequence text) {
            mCancelView.setText(text);
            mCancelView.setVisibility((text == null || "".equals(text.toString())) ? View.GONE : View.VISIBLE);
            return this;
        }

        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setListener(OnListener l) {
            mListener = l;
            return this;
        }

        /**
         * {@link View.OnClickListener}
         */
        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (v == mCancelView) {
                if (mListener != null) {
                    mListener.onCancel(getDialog());
                }
            }
        }


        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (mListener != null) {
                mListener.onSelected(getDialog(), position, mAdapter.getItem(position));
            }
        }

//        @Override
//        protected BaseDialog createDialog(Context context, int themeResId) {
//            if (getGravity() == Gravity.BOTTOM) {
//                return new BaseBottomDialog(context, themeResId);
//            }
//            return super.createDialog(context, themeResId);
//        }
    }

    private static final class MenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        private MenuAdapter(List<String> context) {
            super(R.layout.item_dialog_menu, context);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            int position = helper.getAdapterPosition();
            helper.setText(R.id.tv_dialog_menu_name, getItem(position));
            if (position == 0) {
                // 当前是否只有一个条目
                if (getItemCount() == 1) {
                    helper.setBackgroundRes(R.id.fl_content, R.drawable.dialog_menu_item);
                    helper.setGone(R.id.v_dialog_menu_line, false);
                } else {
                    helper.setBackgroundRes(R.id.fl_content, R.drawable.dialog_menu_item_top);
                    helper.setGone(R.id.v_dialog_menu_line, true);
                }
            } else if (position == getItemCount() - 1) {
                helper.setBackgroundRes(R.id.fl_content, R.drawable.dialog_menu_item_bottom);
                helper.setGone(R.id.v_dialog_menu_line, false);
            } else {
                helper.setBackgroundRes(R.id.fl_content, R.drawable.dialog_menu_item_top);
                helper.setGone(R.id.v_dialog_menu_line, true);
            }
        }

    }

    public interface OnListener {

        /**
         * 选择条目时回调
         */
        void onSelected(Dialog dialog, int position, String text);

        /**
         * 点击取消时回调
         */
        void onCancel(Dialog dialog);
    }
}