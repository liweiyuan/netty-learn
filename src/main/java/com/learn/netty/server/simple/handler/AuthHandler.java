package com.learn.netty.server.simple.handler;

import com.learn.netty.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author :lwy
 * @Date : 2018/10/10 11:02
 * @Description :
 *
 *
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //校验成功后，不需要再次校验。
        if (!LoginUtil.hasLogin(ctx.channel())) {

            //System.err.println("校验结果");
            ctx.channel().close();
        } else {

            //校验成功后，后续的都不需要进行登陆校验
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(LoginUtil.hasLogin(ctx.channel())){
            System.err.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        }else {
            System.err.println("无登录验证，强制关闭连接!");
        }
    }
}
