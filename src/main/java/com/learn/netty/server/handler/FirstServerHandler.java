package com.learn.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author :lwy
 * @Date : 2018/10/9 14:31
 * @Description :
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    //负责客户端发来的数据的读取处理
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buffer= (ByteBuf) msg;

        System.out.println(new Date()+":服务端读取到数据 -> " +buffer.toString(Charset.forName("utf-8")));

        //回复客户端收到数据

        System.err.println(new Date()+":服务端写出数据");
        ByteBuf out=getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        byte[] bytes="你好，我已经收到数据".getBytes(Charset.forName("utf-8"));
        ByteBuf byteBuf=ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
