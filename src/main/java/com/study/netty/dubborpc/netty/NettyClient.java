package com.study.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.*;

public class NettyClient {
    //创建线程池
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //new ThreadPoolExecutor(CPU * 2,CPU * 2,50L, TimeUnit.SECONDS, new SynchronousQueue<>());

    private static NettyClientHandler client;

    //编写方法使用代理模式获取代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),

                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    if (client == null) {
                        initClient();
                    }
                    //设置要发给服务器端的信息
                    client.setParam(providerName + args[0]);
                    return threadPool.submit(client).get();
                });
    }


    private static void initClient() throws InterruptedException {
        client = new NettyClientHandler();
        //创建group
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(client);
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 9999).sync();
//        future.channel().closeFuture().sync();
    }
}
