package com.learn.netty.codec;

import com.learn.netty.protocol.Packet;
import com.learn.netty.protocol.commond.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author :lwy
 * @Date : 2018/10/9 19:10
 * @Description :
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out,msg);
    }
}
