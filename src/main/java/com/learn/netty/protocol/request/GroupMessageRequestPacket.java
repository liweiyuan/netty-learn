package com.learn.netty.protocol.request;

import com.learn.netty.protocol.Packet;

import static com.learn.netty.protocol.commond.Command.GROUP_MESSAGE_REQUEST;

/**
 * @Author :lwy
 * @Date : 2018/10/11 9:34
 * @Description :
 */
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket() {
    }

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }

    public String getToGroupId() {
        return toGroupId;
    }

    public void setToGroupId(String toGroupId) {
        this.toGroupId = toGroupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
