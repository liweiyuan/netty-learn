package com.learn.netty.client.simple.handler;

import com.learn.netty.protocol.request.LoginRequestPacket;
import com.learn.netty.protocol.response.LoginResponsePacket;
import com.learn.netty.session.Session;
import com.learn.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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
        /*if (msg.isSuccess()) {
            //登陆成功后，设置标志位
            SessionUtil.markAsLogin(ctx.channel());
            System.out.println(new Date() + ": 客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + msg.getReason());
        }*/

        String userId = msg.getUserId();
        String userName = msg.getUserName();

        if(msg.isSuccess()){
            System.out.println("[" + userName + "]登录成功，userId 为: " + msg.getUserId());
            SessionUtil.bindSession(new Session(userId,userName),ctx.channel());
        }else {
            System.out.println("[" + userName + "]登录失败，原因：" + msg.getReason());
        }
    }

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        //创建登陆对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("wade");
        packet.setPassword("bosh");

        //TODO 测试校验登陆的逻辑
        ctx.channel().writeAndFlush(packet);
    }*/

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
