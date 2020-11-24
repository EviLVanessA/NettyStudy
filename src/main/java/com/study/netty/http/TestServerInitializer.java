package com.study.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();
        //加入netty提供的httpServerCodec  codec => coder + decoder
        //httpServerCodec 是netty提供HTTP的编解码器
        pipeline.addLast("myHttpServerCodec",new HttpServerCodec());
        //增加一个自定义的Handler
        pipeline.addLast("myHttpServerHandler",new TestHttpServerHandler());
    }
}
