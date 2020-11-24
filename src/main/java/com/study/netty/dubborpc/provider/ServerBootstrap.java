package com.study.netty.dubborpc.provider;

import com.study.netty.dubborpc.netty.NettyServer;

/**
 * 启动服务的提供者 NettyServer
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",9999);
    }
}
