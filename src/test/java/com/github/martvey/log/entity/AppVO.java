package com.github.martvey.log.entity;

public class AppVO {
    private String appName;
    private String userName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "AppVO{" +
                "appName='" + appName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
