package com.learn.netty.util;

import com.learn.netty.attribute.Attributes;
import com.learn.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author :lwy
 * @Date : 2018/10/9 17:16
 * @Description :
 */
public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 绑定Session
     *
     * @param session
     * @param channel
     */
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解除Session绑定
     *
     * @param channel
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 获取渠道
     * @param userId
     * @return
     */
    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }


    //标记为登陆状态
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.SESSION);
    }

    //判断是否登陆 TODO
    public static boolean hasLogin(Channel channel) {
        Attribute<Session> loginAttr = channel.attr(Attributes.SESSION);
        return loginAttr.get() != null;
    }

    /**
     * 获取会话Session
     * @param channel
     * @return
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }
}
