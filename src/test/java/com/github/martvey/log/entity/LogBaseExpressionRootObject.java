package com.github.martvey.log.entity;

/**
 * @version 1.0
 * @author VVnnl
 * @date 2021/2/04 8:31
 */
public class LogBaseExpressionRootObject {
    private String userId;
    private String userName;
    private String currentTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
