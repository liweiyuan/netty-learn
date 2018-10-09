package com.learn.netty.client.handler;

import com.learn.netty.protocol.Packet;
import com.learn.netty.protocol.commond.PacketCodeC;
import com.learn.netty.protocol.request.LoginRequestPacket;
import com.learn.netty.protocol.response.LoginResponsePacket;
import com.learn.netty.protocol.response.MessageResponsePacket;
import com.learn.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @Author :lwy
 * @Date : 2018/10/9 16:13
 * @Description :登陆处理逻辑
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        //创建登陆对象
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUserName("wade");
        packet.setPassword("bosh");

        //编码
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), packet);

        ctx.channel().writeAndFlush(byteBuf);
    }

    //处理登陆服务器后的响应

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {

                //登陆成功后，设置标志位
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }
}
