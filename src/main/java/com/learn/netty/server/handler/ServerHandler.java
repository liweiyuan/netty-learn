package com.learn.netty.server.handler;

import com.learn.netty.protocol.Packet;
import com.learn.netty.protocol.commond.PacketCodeC;
import com.learn.netty.protocol.request.LoginRequestPacket;
import com.learn.netty.protocol.request.MessageRequestPacket;
import com.learn.netty.protocol.response.LoginResponsePacket;
import com.learn.netty.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @Author :lwy
 * @Date : 2018/10/9 16:24
 * @Description : 处理登陆逻辑
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf byteBuf = (ByteBuf) msg;

        //解码
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        //判断请求类型
        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + ": 收到客户端登陆请求……");
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

            loginResponsePacket.setVersion(loginRequestPacket.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                loginResponsePacket.setReason("登陆成功");
                System.out.println(new Date() + ": 登录成功!");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码不正确");
                System.out.println(new Date() + ": 登录失败!");
            }

            //登陆响应
            ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(buffer);
        }else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket= (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket responsePacket=new MessageResponsePacket();
            responsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf buf=PacketCodeC.INSTANCE.encode(ctx.alloc(),responsePacket);
            ctx.channel().writeAndFlush(buf);

        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
