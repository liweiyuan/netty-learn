package com.learn.netty.attribute;

import com.learn.netty.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Author :lwy
 * @Date : 2018/10/9 17:13
 * @Description :
 */
public interface Attributes {
    //AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION=AttributeKey.newInstance("session");
}
