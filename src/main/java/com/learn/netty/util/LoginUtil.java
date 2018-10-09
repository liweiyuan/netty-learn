package com.learn.netty.util;

import com.learn.netty.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @Author :lwy
 * @Date : 2018/10/9 17:16
 * @Description :
 */
public class LoginUtil {

    //标记为登陆状态
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    //判断是否登陆 TODO
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        //Attribute<Boolean> loginAttr = channel.attr(AttributeKey.newInstance("login"));

        //System.out.println("登陆状态:"+loginAttr.get());
        return loginAttr.get() != null;
    }
}
