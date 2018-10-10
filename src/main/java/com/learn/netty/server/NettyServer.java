package com.learn.netty.server;

import com.learn.netty.codec.PacketDecoder;
import com.learn.netty.codec.PacketEncoder;
import com.learn.netty.common.handler.Spliter;
import com.learn.netty.server.simple.handler.LifeCyCleTestHandler;
import com.learn.netty.server.simple.handler.LoginRequestHandler;
import com.learn.netty.server.simple.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        //1.
                        // ch.pipeline().addLast(new FirstServerHandler());
                        //2.服务端处理登录请求
                        //ch.pipeline().addLast(new ServerHandler());

                        //3.处理读数据的逻辑链
                        //ch.pipeline().addLast(new InBoundHandlerA())
                                //.addLast(new InBoundHandlerB())
                                //.addLast(new InBoundHandlerC());

                        //处理写数据的逻辑链
                        //ch.pipeline().addLast(new OutBoundHandlerA())
                                //.addLast(new OutBoundHandlerB())
                                //.addLast(new OutBoundHandlerC());
                        //4.使用pipeline连自动处理，避免过多的if-else
                        //5.添加拆包粘包
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        //6.增加数据校验(魔数校验)
                        ch.pipeline().addLast(new Spliter());

                        //7.channelHandler生命周期
                        ch.pipeline().addLast(new LifeCyCleTestHandler());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });


        bind(serverBootstrap);
    }

    private static void bind(final ServerBootstrap serverBootstrap) {
        serverBootstrap.bind(NettyServer.PORT).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + NettyServer.PORT + "]绑定成功!");
            } else {
                System.err.println("端口[" + NettyServer.PORT + "]绑定失败!");
            }
        });
    }
}
