package com.jlkf.text.textapp.util.decoration;

import java.util.HashMap;

/**
 * Created by win7 on 2018/11/21.
 */

public class Decoration {
    /**
     * RecyclerView 线性布局
     *
     * @param top    上
     * @param bottom 下
     * @param left   左
     * @param right  右
     * @return
     */
    public static RecyclerViewSpacesItemDecoration decoration(int top, int bottom, int left, int right) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, top);//top间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION, bottom);//底部间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION, left);//左间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, right);//右间距
        return new RecyclerViewSpacesItemDecoration(stringIntegerHashMap);
    }

    /**
     * RecyclerView 网格布局
     *
     * @param spanCount   一行几列
     * @param spacing     空格多少
     * @param includeEdge 是否判断最左边和最右边有空格
     * @return
     */
    public static RecyclerViewGridDecoration GridDecoration(int spanCount, int spacing, boolean includeEdge) {
        return new RecyclerViewGridDecoration(spanCount, spacing, includeEdge);
    }
}
