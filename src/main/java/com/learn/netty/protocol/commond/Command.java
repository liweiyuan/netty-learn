package com.learn.netty.protocol.commond;

/**
 * @Author :lwy
 * @Date : 2018/10/9 15:08
 * @Description :
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
