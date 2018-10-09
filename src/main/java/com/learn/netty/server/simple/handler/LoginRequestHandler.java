package com.learn.netty.server.simple.handler;

import com.learn.netty.protocol.request.LoginRequestPacket;
import com.learn.netty.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Author :lwy
 * @Date : 2018/10/9 18:56
 * @Description : 剔除原来的if-else,只关注登陆请求业务
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": 收到客户端登陆请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setReason("登陆成功");
            System.out.println(new Date() + ": 登录成功!");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码不正确");
            System.out.println(new Date() + ": 登录失败!");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket msg) {
        return true;
    }
}
