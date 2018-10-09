package com.learn.netty.client.simple.handler;

import com.learn.netty.protocol.commond.PacketCodeC;
import com.learn.netty.protocol.request.LoginRequestPacket;
import com.learn.netty.protocol.response.LoginResponsePacket;
import com.learn.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;
import java.util.UUID;

/**
 * @Author :lwy
 * @Date : 2018/10/9 19:05
 * @Description :
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {

            //登陆成功后，设置标志位
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(new Date() + ": 客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + msg.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        //创建登陆对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("wade");
        packet.setPassword("bosh");

        ctx.channel().writeAndFlush(packet);
    }
}
