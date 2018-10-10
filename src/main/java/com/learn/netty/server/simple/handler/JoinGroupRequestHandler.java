package com.learn.netty.server.simple.handler;

import com.learn.netty.protocol.request.JoinGroupRequestPacket;
import com.learn.netty.protocol.response.JoinGroupResponsePacket;
import com.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Author :lwy
 * @Date : 2018/10/10 18:10
 * @Description :
 */
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        //1.获取群对应的ChannelGroup,然后田间当前的channel
        String groupId=msg.getGroupId();
        ChannelGroup channels=SessionUtil.getChannelGroup(groupId);

        //2.添加到
        channels.add(ctx.channel());
        // 3. 构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setGroupId(msg.getGroupId());
        responsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
