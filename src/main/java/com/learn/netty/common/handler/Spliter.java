package com.learn.netty.common.handler;

import com.learn.netty.protocol.commond.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author :lwy
 * @Date : 2018/10/10 10:10
 * @Description :
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;

    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    //魔数校验  //版本校验
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //屏蔽非本协议的客户端
        if (in.getInt(in.readerIndex())+1 != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            System.out.println("校验失败，channel被关闭。。。");
            return null;
        }
        return super.decode(ctx, in);
    }
}
