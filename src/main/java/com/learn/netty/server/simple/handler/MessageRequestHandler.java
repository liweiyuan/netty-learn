package com.learn.netty.server.simple.handler;

import com.learn.netty.protocol.request.MessageRequestPacket;
import com.learn.netty.protocol.response.MessageResponsePacket;
import com.learn.netty.session.Session;
import com.learn.netty.util.SessionUtil;
import io.netty.channel.Channel;
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

        //1.获取消息发送方的Session信息
        Session session=SessionUtil.getSession(ctx.channel());

        //System.out.println(new Date() + ": 收到客户端消息: " + msg.getMessage());


        //2.通过消息发送方的会话信息构造发送的消息
        MessageResponsePacket responsePacket=new MessageResponsePacket();
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUserName());
        responsePacket.setMessage(msg.getMessage());

        //3.获取消息接收方的Channel
        Channel toUserChannel=SessionUtil.getChannel(msg.getToUserId());

        //4.将消息发送给消息接受方
        if(toUserChannel!=null&& SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(responsePacket);
        }else {
            System.err.println("[" + session.getUserId() + "] 不在线，发送失败!");
        }
    }
}
