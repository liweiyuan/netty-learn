package com.learn.netty.protocol.request;

import com.learn.netty.protocol.Packet;
import lombok.Data;

import static com.learn.netty.protocol.commond.Command.LOGIN_REQUEST;

/**
 * @Author :lwy
 * @Date : 2018/10/9 15:09
 * @Description :登陆消息格式
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
