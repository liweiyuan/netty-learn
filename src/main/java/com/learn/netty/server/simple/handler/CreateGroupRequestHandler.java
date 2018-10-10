package com.learn.netty.server.simple.handler;

import com.learn.netty.protocol.request.CreateGroupRequestPacket;
import com.learn.netty.protocol.response.CreateGroupResponsePacket;
import com.learn.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author :lwy
 * @Date : 2018/10/10 17:26
 * @Description :
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        //1.创建群聊分组
        //创建一个 channel 分组
        ChannelGroup channels=new DefaultChannelGroup(ctx.executor());

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        userIdList.forEach(userId->{
            Channel channel = SessionUtil.getChannel(userId);
            if(channel!=null){
                channels.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        });
        // 3. 创建群聊创建结果的响应
        CreateGroupResponsePacket responsePacket=new CreateGroupResponsePacket();
        responsePacket.setUserNameList(userNameList);
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(UUID.randomUUID().toString().split("-")[1]);
        // 4. 给每个客户端发送拉群通知
        channels.writeAndFlush(responsePacket);

        System.out.print("群创建成功，id 为[" + responsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + responsePacket.getUserNameList());

        // 5. 保存群组相关的信息
        SessionUtil.bindChannelGroup(responsePacket.getGroupId(), channels);
    }
}
