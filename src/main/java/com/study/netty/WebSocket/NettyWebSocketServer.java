package com.study.netty.WebSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyWebSocketServer {
    public static void main(String[] args) {
        //创建两个线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建配置项
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //基于HTTP协议 所以需要HTTP的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            //以块的方式填写，添加ChunkedWriteHandler
                            pipeline.addLast(new ChunkedWriteHandler());
                            //HTTP协议 数据在传输过程中数据时分段的，HttpObjectAggregator可以将多个段聚合起来
                            //这就是为什么当浏览器发送了大量的数据时，可能会发生多次http请求
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            //对于WebSocket数据是以帧的形式传递
                            //可以看到WebSocketFrame下面有六个子类
                            //浏览器请求时：ws://localhost:7000/hello 表示请求的uri
                            //WebSocketServerProtocolHandler的核心功能为 将一个HTTP协议升级为webSocket协议 保持长连接
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //自定义Handler 用来处理业务逻辑
                            pipeline.addLast(new MyTestWebSocketFrameHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
