package com.jlkf.text.textapp.ui.home.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.util.SeatTable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 影院选座功能
 * 来源：https://github.com/qifengdeqingchen/SeatTable
 * 自定义工具类SeatTable
 */
public class MovieActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.st)
    SeatTable seatTableView;

    @Override
    public int intiLayout() {
        return R.layout.activity_movie;
    }

    @Override
    public void initView() {
        tvTitle.setText("影院选座");
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 0 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 16);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
