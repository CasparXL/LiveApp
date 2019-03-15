package com.jlkf.text.textapp.ui.home.bean;


import com.jlkf.text.textapp.util.index.bean.BaseItem;

/**
 * 联系人列表所需要的对象
 */
public class ItemData extends BaseItem {

    private String title;

    public ItemData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
