package com.learn.netty.protocol.request;

import com.learn.netty.protocol.Packet;
import lombok.Data;

import static com.learn.netty.protocol.commond.Command.MESSAGE_REQUEST;

/**
 * @Author :lwy
 * @Date : 2018/10/9 17:06
 * @Description : 控制台发送消息
 */
@Data
public class MessageRequestPacket extends Packet {

    //指向要发送消息的userId
    private String toUserId;

    private String message;

    public MessageRequestPacket() {
    }

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
