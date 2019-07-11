package com.jlkf.text.textapp.bean.dao;

import com.jlkf.text.textapp.app.BaseApplication;

import java.util.List;

import io.objectbox.Box;

public class UserDao {

    public static void insertOrUpdateBlogItem(User newItem) {
        getBlogItemBox().put(newItem);
    }

    public static List<User> getAll() {
        return getBlogItemBox().getAll();
    }

    public static void delete(long id) {
        getBlogItemBox().remove(id);
    }

    private static Box<User> getBlogItemBox() {
        return BaseApplication.getInstance().getBoxStore().boxFor(User.class);
    }
}