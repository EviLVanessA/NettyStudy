package com.study.netty.Simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建BossGroup和 WorkerGroup
        //bossGroup仅处理连接请求，真正和客户端进行业务处理的会交给workGroup来处理
        //bossGroup和workerGroup含有的子线程(NioEventLoop)的个数为 ：CPU核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        //创建服务器端启动对象，配置参数
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来进行设置
            bootstrap.group(bossGroup,workerGroup)                       //设置两个线程组
                    .channel(NioServerSocketChannel.class)               //使用 NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)           //设置线程队列得到连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)   //设置保持活动连接的状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {     //创建一个通道初始化对象
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });


            System.out.println("Server is ready...");
            //绑定一个端口并且同步处理，生成了一个ChannelFuture对象,启动服务器
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //对关闭对象进行监听
            cf.channel().closeFuture().sync();
        }finally{
            //进行优雅的关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
