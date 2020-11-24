package com.study.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //服务器回送的消息
        byte[] content = msg.getContent();
        String s = new String(content, CharsetUtil.UTF_8);
        System.out.println("服务器回送的消息：" + s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送5条数据
        for (int i = 0; i < 5; i++) {
            String msg = "hello,java" + UUID.randomUUID().toString().substring(0,i);
            byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
            int length = bytes.length;
            //创建协议包
            ctx.writeAndFlush(new MessageProtocol(length,bytes));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }


}
