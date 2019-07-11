package com.jlkf.text.textapp.util;
/**
 * 隐藏软键盘
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.INPUT_METHOD_SERVICE;

//打开或关闭软键盘
public class KeyBoardUtils {
    /**
     * 打开软键盘
     * 魅族可能会有问题
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeyBord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void closeKeyBord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    public static void hideInputForce(Activity activity) {
        if (activity == null || activity.getCurrentFocus() == null)
            return;

        if (((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE)) != null) {
            ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public static void dismissInput(final Context context, final EditText mEditText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() { //让软键盘延时弹出，以更好的加载Activity
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
            }

        }, 200);
    }
 /**
     * des:隐藏软键盘,这种方式参数为activity
     *
     * @param activity
     */
    public static void hideInputForce(Fragment activity) {
        Objects.requireNonNull(activity.getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 打开键盘
     **/
    public static void showInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }


}