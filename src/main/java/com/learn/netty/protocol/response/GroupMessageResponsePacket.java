package com.learn.netty.protocol.response;

import com.learn.netty.protocol.Packet;
import com.learn.netty.session.Session;

import static com.learn.netty.protocol.commond.Command.GROUP_MESSAGE_RESPONSE;

/**
 * @Author :lwy
 * @Date : 2018/10/11 9:36
 * @Description :
 */
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }

    public String getFromGroupId() {
        return fromGroupId;
    }

    public void setFromGroupId(String fromGroupId) {
        this.fromGroupId = fromGroupId;
    }

    public Session getFromUser() {
        return fromUser;
    }

    public void setFromUser(Session fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
