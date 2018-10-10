package com.learn.netty.session;

/**
 * @Author :lwy
 * @Date : 2018/10/10 14:13
 * @Description :
 */
public class Session {
    // 用户唯一性标识
    private String userId;
    private String userName;

    public Session() {
    }

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

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
}
