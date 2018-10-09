package com.learn.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author :lwy
 * @Date : 2018/10/9 17:57
 * @Description :
 */
public class InBoundHandlerB extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("InBoundHandlerB: " + msg);
        super.channelRead(ctx, msg);
    }
}
