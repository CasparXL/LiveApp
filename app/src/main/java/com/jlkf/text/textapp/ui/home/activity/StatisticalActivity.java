package com.jlkf.text.textapp.ui.home.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.base.BaseActivity;
import com.jlkf.text.textapp.base.NoContract;
import com.jlkf.text.textapp.base.NoPresenter;
import com.jlkf.text.textapp.databinding.ActivityStatisticalBinding;
import com.jlkf.text.textapp.injection.component.ApplicationComponent;
import com.jlkf.text.textapp.injection.component.DaggerHttpComponent;
import com.jlkf.text.textapp.view.MyMarkerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 功能：统计图表
 * 依赖来源：https://github.com/PhilJay/MPAndroidChart
 * 依赖：com.github.PhilJay:MPAndroidChart:v3.1.0-alpha
 */
public class StatisticalActivity extends BaseActivity<NoPresenter, ActivityStatisticalBinding> implements NoContract.View {

    List<String> mList = new ArrayList<>();
    List<Integer> mIntList = new ArrayList<>();


    @Override
    public int setContentLayout() {
        return R.layout.activity_statistical;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder().applicationComponent(appComponent).build().inject(this);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        bindingView.view.tvTitle.setText("图表");
        bindingView.view.ivBack.setOnClickListener(v -> finish());
        //显示边界
        bindingView.lineChart.setDrawBorders(false);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float f = (float) ((Math.random()) * 80);
            entries.add(new Entry(i, f));
            mIntList.add((int) f);
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
//设置显示值的字体大小
        lineDataSet.setValueTextSize(9f);
//线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData data = new LineData(lineDataSet);
        bindingView.lineChart.setData(data);
        //1.得到X轴：
        XAxis xAxis = bindingView.lineChart.getXAxis();
        //2.设置X轴的位置（默认在上方）：
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        //3.设置X轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        xAxis.setGranularity(1f);
        //4.设置X轴的刻度数量
        xAxis.setLabelCount(12, true);
        //5.设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        /*xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(20f);*/
        mList.add("一月");
        mList.add("二月");
        mList.add("三月");
        mList.add("四月");
        mList.add("五月");
        mList.add("六月");
        mList.add("七月");
        mList.add("八月");
        mList.add("九月");
        mList.add("十月");
        mList.add("十一月");
        mList.add("十二月");
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mList.get((int) value); //mList为存有月份的集合
            }
        });
        //得到Y轴
        YAxis yAxis = bindingView.lineChart.getAxisLeft();
        YAxis rightYAxis = bindingView.lineChart.getAxisRight();
        //设置Y轴是否显示
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        //设置y轴坐标之间的最小间隔
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1);
        //设置y轴的刻度数量
        //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
        yAxis.setLabelCount(Collections.max(mIntList) + 2, false);
        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        //+1:y轴多一个单位长度，为了好看
        yAxis.setAxisMaximum(Collections.max(mIntList) + 1);
        //y轴
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }
        });
        //图例：得到Lengend
        Legend legend = bindingView.lineChart.getLegend();
        //隐藏Lengend
        legend.setEnabled(false);
        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        bindingView.lineChart.setDescription(description);
        //折线图点的标记
        MyMarkerView mv = new MyMarkerView(this);
        bindingView.lineChart.setMarker(mv);
        //图标刷新
        bindingView.lineChart.invalidate();
        /*//解决滑动冲突
        bindingView.lineChart.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        scrollview.requestDisallowInterceptTouchEvent(true);
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                    {
                        scrollview.requestDisallowInterceptTouchEvent(false);
                        break;
                    }
                }
                return false;
            }
        });*/

    }

    @Override
    public void initIntent() {

    }

}
