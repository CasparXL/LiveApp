package com.jlkf.text.textapp.ui.home.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.ActivityMovieBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.util.SeatTable;


/**
 * 影院选座功能
 * 来源：https://github.com/qifengdeqingchen/SeatTable
 * 自定义工具类SeatTable
 */
public class MovieActivity extends BaseActivity<NoPresenter, ActivityMovieBinding> implements NoContract.View {

    

    @Override
    public int setContentLayout() {
        return R.layout.activity_movie;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);

    }

    @Override
    public void initView() {
        bindingView.include2.tvTitle.setText("影院选座");
        bindingView.st.setScreenName("8号厅荧幕");//设置屏幕名称
         bindingView.st.setMaxSelected(3);//设置最多选中

         bindingView.st.setSeatChecker(new SeatTable.SeatChecker() {

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
         bindingView.st.setData(10, 16);
        bindingView.include2.ivBack.setOnClickListener(v -> finish());

    }

    @Override
    public void initIntent() {

    }

}
