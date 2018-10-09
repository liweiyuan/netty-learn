package com.learn.netty.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.learn.netty.serialize.Serializer;
import com.learn.netty.serialize.SerializerAlgorithm;

/**
 * @Author :lwy
 * @Date : 2018/10/9 15:17
 * @Description :采用阿里的序列化组件
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clz, byte[] bytes) {
        return JSON.parseObject(bytes, clz);
    }
}
