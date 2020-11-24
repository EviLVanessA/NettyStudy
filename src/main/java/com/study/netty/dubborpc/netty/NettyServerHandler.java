package com.study.netty.dubborpc.netty;

import com.study.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息并调用我们的服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器API时，我们需要定义一个协议
        //比如我们要求每次发消息时 都必须以某个字符串开头“helloService#hello#”
        if (msg.toString().startsWith("HelloService#hello#")){
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
