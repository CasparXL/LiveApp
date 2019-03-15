package com.jlkf.text.textapp.util.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

public class RecyclerViewSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private HashMap<String, Integer> mSpaceValueMap;

     static final String TOP_DECORATION = "top_decoration";
    static final String BOTTOM_DECORATION = "bottom_decoration";
     static final String LEFT_DECORATION = "left_decoration";
     static final String RIGHT_DECORATION = "right_decoration";

    RecyclerViewSpacesItemDecoration(HashMap<String, Integer> mSpaceValueMap) {
        this.mSpaceValueMap = mSpaceValueMap;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mSpaceValueMap.get(TOP_DECORATION) != null)
            outRect.top = mSpaceValueMap.get(TOP_DECORATION);
        if (mSpaceValueMap.get(LEFT_DECORATION) != null)
            outRect.left = mSpaceValueMap.get(LEFT_DECORATION);
        if (mSpaceValueMap.get(RIGHT_DECORATION) != null)
            outRect.right = mSpaceValueMap.get(RIGHT_DECORATION);
        if (mSpaceValueMap.get(BOTTOM_DECORATION) != null)
            outRect.bottom = mSpaceValueMap.get(BOTTOM_DECORATION);
    }


}