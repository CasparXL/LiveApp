package com.jlkf.text.textapp.view;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.jlkf.text.textapp.R;
import com.jlkf.text.textapp.util.LogUtil;

import java.text.DecimalFormat;

public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private DecimalFormat format = new DecimalFormat("##0");

    public MyMarkerView(Context context) {
        super(context, R.layout.layout_markerview);//这个布局自己定义
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    //显示的内容
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(format(e.getX()) + "\n" + format.format(e.getY()) + "口");
        LogUtil.e(e.getX() + "\n" + format.format(e.getY()) + "口");
        super.refreshContent(e, highlight);
    }

    //标记相对于折线图的偏移量
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    //时间格式化（显示今日往前30天的每一天日期）
    public String format(float x) {
        CharSequence format = DateFormat.format("MM月dd日",
                System.currentTimeMillis() - (long) (30 - (int) x) * 24 * 60 * 60 * 1000);
        return format.toString();
    }
}
