package com.learn.netty.protocol;

import lombok.Data;

/**
 * @Author :lwy
 * @Date : 2018/10/9 14:55
 * @Description : 定义数据包格式
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     */
    public abstract Byte getCommand();

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }
}
