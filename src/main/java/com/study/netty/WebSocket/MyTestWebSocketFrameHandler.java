package com.study.netty.WebSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;


/**
 * TextWebSocketFrame类型表示一个文本帧(frame)
 */
public class MyTestWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("Server receive the msg ：" + msg.text());
        //回复浏览器
        ctx.writeAndFlush(new TextWebSocketFrame("服务器时间:" + LocalDateTime.now() + "\t消息\t" + msg.text()));

    }

    /**
     * 当web客户端连接后，就会触发此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id表示唯一的一个值，LongText是唯一额 ShortText不是唯一的
        System.out.println("HandlerAdded被调用了\t" + ctx.channel().id().asLongText());
    }

    /**
     * 当web客户端失去连接后，就会触发此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用了\t" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生" + cause.getMessage());
        //关闭连接
        ctx.close();
    }
}
