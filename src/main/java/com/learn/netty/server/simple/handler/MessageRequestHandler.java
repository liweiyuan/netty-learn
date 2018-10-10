package com.learn.netty.server.simple.handler;

import com.learn.netty.protocol.request.MessageRequestPacket;
import com.learn.netty.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Author :lwy
 * @Date : 2018/10/9 19:01
 * @Description : 只关注控制台消息的处理
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": 收到客户端消息: " + msg.getMessage());

        MessageResponsePacket responsePacket=new MessageResponsePacket();
        responsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");
        //ByteBuf buf=PacketCodeC.INSTANCE.encode(ctx.alloc(),responsePacket);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
