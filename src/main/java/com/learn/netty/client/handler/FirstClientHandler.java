package com.learn.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author :lwy
 * @Date : 2018/10/9 14:22
 * @Description :
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {


    //建立连接后，向服务端发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+":客户端写出数据");

        //1.获取数据
        ByteBuf buffer=getByteBuffer(ctx);
        //2.写数据
        ctx.channel().writeAndFlush(buffer);
    }

    //读取数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer= (ByteBuf) msg;

        System.err.println(new Date() + ": 客户端读到数据 -> " + buffer.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuffer(ChannelHandlerContext ctx) {

        //1.获取二进制抽象对象byteBuf
        ByteBuf buffer=ctx.alloc().buffer();
        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "你好，wade!".getBytes(Charset.forName("utf-8"));
        // 3.填充数据
        buffer.writeBytes(bytes);

        return buffer;
    }
}
