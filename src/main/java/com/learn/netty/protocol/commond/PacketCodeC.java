package com.learn.netty.protocol.commond;

import com.learn.netty.protocol.Packet;
import com.learn.netty.protocol.request.*;
import com.learn.netty.protocol.response.*;
import com.learn.netty.serialize.Serializer;
import com.learn.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.learn.netty.protocol.commond.Command.*;

/**
 * @Author :lwy
 * @Date : 2018/10/9 15:23
 * @Description :数据编码到通信协议中
 */
public class PacketCodeC {

    //魔数
    public static final int MAGIC_NUMBER = 0x12345678;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    private final Map<Byte, Serializer> serializerMap;

    //单例模式
    public static final PacketCodeC INSTANCE = new PacketCodeC();


    //TODO 后续可通过注解进行扫描
    public PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }


    /**
     * 编码
     */
    public ByteBuf encode(Packet packet) {
        //1.创建ByteBuf
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer(); //直接内存

        //2.序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.实际编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        //对象
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 编码
     *
     * @param alloc
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBufAllocator alloc, Packet packet) {
        //1.创建ByteBuf
        ByteBuf byteBuf = alloc.ioBuffer(); //直接内存
        //2.序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.实际编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        //对象
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 直接去除ByteBuf生成过程
     *
     * @param byteBuf
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        //2.序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.实际编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        //对象
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 解码
     */
    public Packet decode(ByteBuf byteBuf) {
        //跳过magic
        byteBuf.skipBytes(4);
        //版本
        byteBuf.skipBytes(1);
        //序列化表示
        byte serializeAlgorithm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();
        //字节数组
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        //获取指令（请求类型）
        Class<? extends Packet> requestType = getRequestType(command);
        //获取编码类型
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }


    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }


}
