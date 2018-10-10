package com.learn.netty.server.simple.handler;

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
 * @Date : 2018/10/9 18:56
 * @Description : 剔除原来的if-else,只关注登陆请求业务
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": 收到客户端登陆请求……");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        loginResponsePacket.setUserName(msg.getUserName());
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setReason("登陆成功");
            //8.生成session
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            SessionUtil.bindSession(new Session(userId, msg.getUserName()), ctx.channel());
            System.out.println(new Date() + " : " + msg.getUserName() + ": 登录成功!");

            //设置登陆成功标值
            //SessionUtil.markAsLogin(ctx.channel());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码不正确");
            System.out.println(new Date() + ": 登录失败!");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }


    /**
     * 用户断线后取消Session的绑定
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    private boolean valid(LoginRequestPacket msg) {
        return true;
    }
}
