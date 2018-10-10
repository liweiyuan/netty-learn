package com.learn.netty.protocol.response;

import com.learn.netty.protocol.Packet;

import static com.learn.netty.protocol.commond.Command.MESSAGE_RESPONSE;

/**
 * @Author :lwy
 * @Date : 2018/10/9 17:08
 * @Description :
 */
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
}
