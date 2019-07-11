package com.jlkf.text.textapp.bean;


import com.jlkf.text.textapp.network.BaseResponse;

/**
 * "浪小白" 创建 2019/2/27.
 * 界面名称以及功能: 启动页实体类
 */
public class SplashBean extends BaseResponse {


    private long advertisingId;
    private String advertisingName;
    private String resUrl;
    private String linkUrl;
    private int resOrder;
    private int location;
    private int isShow;
    private Object crtTime;
    private String remark;

    public long getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(long advertisingId) {
        this.advertisingId = advertisingId;
    }

    public String getAdvertisingName() {
        return advertisingName;
    }

    public void setAdvertisingName(String advertisingName) {
        this.advertisingName = advertisingName;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getResOrder() {
        return resOrder;
    }

    public void setResOrder(int resOrder) {
        this.resOrder = resOrder;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public Object getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Object crtTime) {
        this.crtTime = crtTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
