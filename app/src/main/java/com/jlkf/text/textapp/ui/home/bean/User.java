package com.jlkf.text.textapp.ui.home.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 数据库实体类测试
 */

/**
 * 实体类的注解
 */
@Entity
public class User {
    /*@Entity 表示这个实体类一会会在数据库中生成对应的表，

    @Id 表示该字段是id，注意该字段的数据类型为包装类型Long

    @Property 则表示该属性将作为表的一个字段，其中nameInDb看名字就知道这个属性在数据库中对应的数据名称。

    @Transient 该注解表示这个属性将不会作为数据表中的一个字段。

    @NotNull 表示该字段不可以为空

    @Unique 表示该字段唯一。小伙伴们有兴趣可以自行研究。*/
    @Id
    @Unique
    private Long id;
    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "SEX")
    private String sex;
    @Property(nameInDb = "IDCARD")
    private String idCard;
    @Transient
    private int tempUsageCount; // not persisted

    @Generated(hash = 606060690)
    public User(Long id, String name, String sex, String idCard) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.idCard = idCard;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getIdCard() {
        return this.idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

}
