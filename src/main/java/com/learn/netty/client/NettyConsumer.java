package com.learn.netty.client;

import com.learn.netty.client.handler.ClientHandler;
import com.learn.netty.client.handler.FirstClientHandler;
import com.learn.netty.protocol.commond.PacketCodeC;
import com.learn.netty.protocol.request.MessageRequestPacket;
import com.learn.netty.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Author :lwy
 * @Date : 2018/9/29 13:07
 * @Description :
 */
public class NettyConsumer {

    static final int MAX_RETRY = 50;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        //处理数据
                        //ch.pipeline().addLast(new FirstClientHandler());
                        //处理登陆
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, final int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                //开启控制台发送消息的线程
                Channel channel = ((ChannelFuture) future).channel();

                startConsoleThread(channel);

                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);

            }
        });
    }

    /**
     * 启动控制台线程
     *
     * @param channel
     */
    private static void startConsoleThread(Channel channel) {

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.err.println("输入消息至服务端");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();

                    MessageRequestPacket messageRequestPacket = new
                            MessageRequestPacket();
                    messageRequestPacket.setMessage(line);
                    ByteBuf byteBuf=PacketCodeC.INSTANCE.encode(channel.alloc(),messageRequestPacket);

                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
