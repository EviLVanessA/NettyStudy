package com.study.netty.Simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //客户端只需要一个循环事件组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建客户端的启动对象 客户端使用的是BootStrap 不是 ServerBootStrap
        Bootstrap bootstrap = new Bootstrap();
        try {
            //设置相关参数
            bootstrap.group(workerGroup)                            //设置线程组
                    .channel(NioSocketChannel.class)                //设置客户端通道实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("Client is ready ....");

            //启动客户端连接服务端 channelFuture涉及到了netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅的关闭
            workerGroup.shutdownGracefully();
        }

    }
}
