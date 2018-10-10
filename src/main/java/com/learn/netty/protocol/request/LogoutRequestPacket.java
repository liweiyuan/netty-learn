package com.learn.netty.protocol.request;

import com.learn.netty.protocol.Packet;
import lombok.Data;

import static com.learn.netty.protocol.commond.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
