package com.learn.netty.client.simple.handler;

import com.learn.netty.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Author :lwy
 * @Date : 2018/10/9 19:09
 * @Description :
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(new Date() + ": 收到服务端的消息: " + msg.getMessage());
    }
}
