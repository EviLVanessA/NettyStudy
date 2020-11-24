package com.study.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyGroupChatServer {
    //定义相关的属性
    //端口
    private int port;

    public NettyGroupChatServer(int port) {
        this.port = port;
    }

    /**
     * 编写run方法，处理客户端的请求
     */
    public void run() throws InterruptedException {
        //创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // capacity 默认CPU*2  6*2 = 12
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建配置项
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //获取到pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline中加入一个解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //向pipeline中加入一个编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //加入自己的业务处理handler
                            pipeline.addLast("myHandler",new NettyGroupChatServerHandler());
                        }
                    });

            System.out.println("Netty Server is ready");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyGroupChatServer(9999).run();
    }
}
