package com.jlkf.text.textapp.bean;

import java.util.List;

public class ConnectBean  {
    private int level;
    private List<DeviceBean> beans;

    public ConnectBean(int level) {
        this.level = level;
    }

    public List<DeviceBean> getBeans() {
        return beans;
    }

    public void setBeans(List<DeviceBean> beans) {
        this.beans = beans;
    }

    public static class DeviceBean {
        private int type;
        private String name;
        private String time;
        private String upload;
        private int signLevel;
        private boolean isConnect;

        public boolean isConnect() {
            return isConnect;
        }

        public void setConnect(boolean connect) {
            isConnect = connect;
        }

        public DeviceBean(String name) {
            this.name = name;
        }

        public DeviceBean(int type, String name, String time, String upload, int signLevel, boolean isConnect) {
            this.type = type;
            this.name = name;
            this.time = time;
            this.upload = upload;
            this.signLevel = signLevel;
            this.isConnect = isConnect;
        }

        public int getSignLevel() {
            return signLevel;
        }

        public void setSignLevel(int signLevel) {
            this.signLevel = signLevel;
        }


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUpload() {
            return upload;
        }

        public void setUpload(String upload) {
            this.upload = upload;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}