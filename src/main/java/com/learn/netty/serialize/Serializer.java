package com.learn.netty.serialize;

import com.learn.netty.serialize.impl.JSONSerializer;

/**
 * @Author :lwy
 * @Date : 2018/10/9 15:12
 * @Description :
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换为二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换为java 对象
     */

    <T> T deserialize(Class<T> clz, byte[] bytes);
}
