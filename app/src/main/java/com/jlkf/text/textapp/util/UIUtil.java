package com.jlkf.text.textapp.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.jlkf.text.textapp.util.image.MeasureUtil;


/**
 * "浪小白" 创建 2019/7/3.
 * 界面名称以及功能:
 */

public class UIUtil {
    /**
     * 修改部分颜色
     *
     * @param first
     * @param content 给content这个内容设置颜色color,且字体大小设置为spValue
     * @param end
     * @param color
     * @return
     */
    public static SpannableString setTextPartSizeColor(Context context, String first, String content, String end, int color) {
        SpannableString spannableSale = new SpannableString(first + content + end);
        if (context != null) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(color));//设置颜色样式
            spannableSale.setSpan(colorSpan, first.length(), first.length() + content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableSale;
        } else {
            return spannableSale;
        }
    }

    /**
     * 修改部分颜色以及字体
     *
     * @param first
     * @param content 给content这个内容设置颜色color,且字体大小设置为spValue
     * @param end
     * @param color
     * @return
     */
    public static SpannableString setTextPartSizeColor(Context context, String first, String content, String end, int color, float spValue) {
        SpannableString spannableSale = new SpannableString(first + content + end);
        if (context != null) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(color));//设置颜色样式
            spannableSale.setSpan(colorSpan, first.length(), first.length() + content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableSale.setSpan(new AbsoluteSizeSpan(MeasureUtil.sp2px(context, spValue)), first.length(), first.length() + content.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableSale;
        } else {
            return spannableSale;
        }
    }

    /**
     * 改变字体颜色并改变图标
     *
     * @param context    上下文
     * @param textView   文本对象
     * @param drawableId 资源文件
     * @param colorId    字体颜色
     * @param how        图标方向 1左 2上 3右 4下
     */
    public static void setDrawable(Context context, TextView textView, int drawableId, int colorId, int how) {
        if (context != null) {
            Drawable dra = null;
            if (drawableId != 0) {
                dra = context.getResources().getDrawable(drawableId);
                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
            }
            if (colorId != 0)
                textView.setTextColor(context.getResources().getColor(colorId));
            switch (how) {
                case 1:
                    textView.setCompoundDrawables(dra, null, null, null);
                    break;
                case 2:
                    textView.setCompoundDrawables(null, dra, null, null);
                    break;
                case 3:
                    textView.setCompoundDrawables(null, null, dra, null);
                    break;
                case 4:
                    textView.setCompoundDrawables(null, null, null, dra);
                    break;
                default:
                    //context.getResources().getDrawable(android.R.color.transparent)在某些版本设置null无效，需要设置这个属性
                    textView.setCompoundDrawables(null, null, null, null);
                    break;
            }
        }
    }

    public static String isNullString(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        } else {
            return s;
        }
    }
}
