package com.study.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NettyGroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个Channel group 用来管理所有的Channel  需要有全局事件执行器 为一个单例
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //时间格式化器
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 当连接被建立 第一个被执行的方法 将当前channel加入到channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户端
        //该方法会将组中所有的channel遍历并发送消息
        channelGroup.writeAndFlush("[Client]：" + channel.remoteAddress() + "加入聊天室");
        channelGroup.add(channel);
    }

    /**
     * 表示断开连接会被触发 将XX客户离开信息推送给当前在线的用户
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[Client]：" + sdf.format(new Date()) + "\t" + channel.remoteAddress() + "离开聊天室");
//        channelGroup.remove(channel);自己会去掉
        System.out.println("当前Group大小：" + channelGroup.size());
    }

    /**
     * 表示channel处于活动的状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "已上线~");
    }

    /**
     * 表示channel处于非活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "已下线~");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取到当前Channel
        Channel channel = ctx.channel();
        //这时遍历 ChannelGroup ，根据不同的情况，发送不同的消息
        channelGroup.forEach(ch -> {

            if (channel != ch){
                ch.writeAndFlush("[Client]" + channel.remoteAddress() + "\tsay" + msg + "\n");
            }else {
                ch.writeAndFlush("[Self]" + channel.remoteAddress() + "\tsay" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
