package com.learn.netty.protocol.response;

import com.learn.netty.protocol.Packet;

import static com.learn.netty.protocol.commond.Command.JOIN_GROUP_RESPONSE;

/**
 * @Author :lwy
 * @Date : 2018/10/10 18:07
 * @Description :
 */
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_RESPONSE;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
