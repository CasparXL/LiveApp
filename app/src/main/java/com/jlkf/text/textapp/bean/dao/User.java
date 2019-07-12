package com.jlkf.text.textapp.bean.dao;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Uid;

@Entity
@Uid(8503529349746881264L)
public class User {
    @Id
    private long id;
    private String name;
    private String sex;
    private String idCard;

    public User(long id, String name, String sex, String idCard) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.idCard = idCard;
    }

    public User(String name, String sex, String idCard) {
        this.name = name;
        this.sex = sex;
        this.idCard = idCard;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}