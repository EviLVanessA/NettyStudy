package com.study.netty.Simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 自定义Handler需要继承Netty规定好的某个HandlerAdapter
 * 这是我们自定义的Handler才能称为一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送的消息
     * @param ctx 是上下文对象 包含了管道pipeline 通道channel 地址
     * @param msg 客户端发送的数据 默认为Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("服务器读取线程\t" + Thread.currentThread().getName());
        System.out.println("Server ctx = " + ctx);
        //将msg转成ByteBuf ByteBuf是Netty提供的、性能更高
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buffer.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址为：" + ctx.channel().remoteAddress());


    }

    /**
     * 数据读取完毕
     * @param ctx 是上下文对象 包含了管道pipeline 通道channel 地址
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存并刷新缓存 一般来讲 发送的数据需要进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,Client (>^ω^<)喵", CharsetUtil.UTF_8));
    }

    /**
     * 如果发生异常 关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
