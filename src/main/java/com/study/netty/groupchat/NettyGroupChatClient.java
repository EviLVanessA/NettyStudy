package com.study.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class NettyGroupChatClient {
    //定义相关的属性
    private final String HOST;
    private final int PORT;

    public NettyGroupChatClient(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
    }

    /**
     * 客户端启动方法
     */
    public void run() throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        //设置相关配置
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //得到pipeline
                    ChannelPipeline pipeline = ch.pipeline();
                    //加入相关的handler
                    pipeline.addLast("decoder",new StringDecoder())
                            .addLast("encoder",new StringEncoder())
                            .addLast("myHandler",new NettyGroupChatClientHandler());

                }
            });
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            Channel channel = future.channel();
            System.out.println("-----------" + channel.localAddress() + "\tis ready");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                //通过channel发送的服务器端
                channel.writeAndFlush(msg + "\r\n");
            }
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyGroupChatClient("127.0.0.1",9999).run();
    }
}
