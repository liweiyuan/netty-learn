package com.learn.netty.protocol.request;

import com.learn.netty.protocol.Packet;

import static com.learn.netty.protocol.commond.Command.JOIN_GROUP_REQUEST;

/**
 * @Author :lwy
 * @Date : 2018/10/10 18:04
 * @Description :
 */
public class JoinGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
