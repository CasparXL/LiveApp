package com.jlkf.text.textapp.ui.home.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.ActivitySuperCalendarBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.util.LogUtil;
import com.jlkf.text.textapp.view.Calendar.CustomDayView;
import com.jlkf.text.textapp.view.Calendar.ThemeDayView;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 功能：日历
 * 依赖来源：https://github.com/MagicMashRoom/SuperCalendar
 * 依赖：implementation 'com.github.MagicMashRoom:SuperCalendar:1.6'
 */
@SuppressLint("SetTextI18n")
public class SuperCalendarActivity extends BaseActivity<NoPresenter, ActivitySuperCalendarBinding> implements NoContract.View {


    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();


    @Override
    public int setContentLayout() {
        return R.layout.activity_super_calendar;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);

    }

    @Override
    public void initView() {
        bindingView.view.tvRight.setVisibility(View.VISIBLE);
        bindingView.view.tvRight.setText("今日");
        bindingView.view.tvRight.setOnClickListener(v -> onClickBackToDayBtn());
        bindingView.view.ivBack.setOnClickListener(v -> finish());
        bindingView.calendarView.setViewHeight(Utils.dpi2px(this, 270));

        initCurrentDate();
        initCalendarView();
        initToolbarClickListener();

    }

    @Override
    public void initIntent() {

    }




    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    /*
     * 如果你想以周模式启动你的日历，请在onResume是调用
     * Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
     * calendarAdapter.switchToWeek(monthPager.getRowIndex());
     * */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化对应功能的listener
     *
     * @return void
     */
    private void initToolbarClickListener() {
        /*
        scrollSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAdapter.getCalendarType() == CalendarAttr.CalendarType.WEEK) {
                    Utils.scrollTo(content, rvToDoList, monthPager.getViewHeight(), 200);
                    calendarAdapter.switchToMonth();
                } else {
                    Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
                    calendarAdapter.switchToWeek(monthPager.getRowIndex());
                }
            }
        });
        themeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshSelectBackground();
            }
        });
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //下一月
                monthPager.setCurrentItem(monthPager.getCurrentPosition() + 1);
            }
        });
        lastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //上一月
                monthPager.setCurrentItem(monthPager.getCurrentPosition() - 1);
            }
        });
        */
    }

    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        bindingView.view.tvTitle.setText(currentDate.getYear() + "年" + currentDate.getMonth() + "月");

    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
                //LogUtil.e(date.getYear()+"年"+date.getMonth()+"月"+date.getDay()+"日");
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                bindingView.calendarView.selectOtherMonth(offset);
            }
        };
        CustomDayView customDayView = new CustomDayView(this, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                this,
                onSelectDateListener,
                CalendarAttr.CalendarType.MONTH,
                CalendarAttr.WeekArrayType.Sunday, customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
                bindingView.list.scrollToPosition(0);
            }
        });
        initMarkData();
        initMonthPager();
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2018-11-29", "1");
        markData.put("2018-11-30", "0");
        markData.put("2018-12-3", "1");
        markData.put("2018-12-4", "0");
        calendarAdapter.setMarkData(markData);
       /*
       再次标记出现问题使用该方法就不会跳过一个月
       Utils.setMarkData(markData);
        calendarAdapter.invalidateCurrentCalendar();*/
    }


    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        bindingView.view.tvTitle.setText(date.getYear() + "年" + date.getMonth() + "月");

    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     */
    private void initMonthPager() {
        bindingView.calendarView.setAdapter(calendarAdapter);
        bindingView.calendarView.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        bindingView.calendarView.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        bindingView.calendarView.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    bindingView.view.tvTitle.setText(date.getYear() + "年" + date.getMonth() + "月");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        bindingView.view.tvTitle.setText(today.getYear() + "年" + today.getMonth() + "月");
    }

    private void refreshSelectBackground() {
        ThemeDayView themeDayView = new ThemeDayView(this, R.layout.custom_day_focus);
        calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }


}
