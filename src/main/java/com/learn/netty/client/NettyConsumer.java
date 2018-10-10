package com.learn.netty.client;

import com.learn.netty.client.simple.handler.LoginResponseHandler;
import com.learn.netty.client.simple.handler.MessageResponseHandler;
import com.learn.netty.codec.PacketDecoder;
import com.learn.netty.codec.PacketEncoder;
import com.learn.netty.common.handler.Spliter;
import com.learn.netty.protocol.commond.PacketCodeC;
import com.learn.netty.protocol.request.LoginRequestPacket;
import com.learn.netty.protocol.request.MessageRequestPacket;
import com.learn.netty.util.SessionUtil;
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
                        //1.处理数据
                        //ch.pipeline().addLast(new FirstClientHandler());
                        //2.3处理登陆
                        //ch.pipeline().addLast(new ClientHandler());

                        //4.剔除过多的if-else
                        //5.添加拆包粘包
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        //6.增加数据校验(魔数校验)
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, final int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                //开启控制台发送消息的线程
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
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
        Scanner scanner = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        new Thread(() -> {
            while (!Thread.interrupted()) {

                //创建连接
                if (!SessionUtil.hasLogin(channel)) {
                    System.err.println("输入用户名登陆");

                    String userName = scanner.nextLine();
                    loginRequestPacket.setUserName(userName);
                    //密码
                    loginRequestPacket.setPassword("wade");

                    channel.writeAndFlush(loginRequestPacket);

                    //睡眠
                    waitForLoginResponse();
                } else {

                    //发送消息格式
                    String toUserId = scanner.next();
                    String message = scanner.next();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
