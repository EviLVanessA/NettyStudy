package com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {

        //创建ServerSocketChannel ——>类似于IO的ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到Selector对象
        Selector selector = Selector.open();
        //绑定一个端口号6666，在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel 注册到 selector 关心时间为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true){
            if (selector.select(1000) == 0){//无事件发生
                System.out.println("无事件发生");
                continue;
            }
            //如果返回的不是0 拿到有事件发生的selectionKey的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //通过selectionKeys拿到所有的SocketChannel
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //获取到selectionKey
                if (key.isAcceptable()){//如果是OP_ACCEPT事件
                    //生成SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功了一个channel" + socketChannel.hashCode());

                    socketChannel.configureBlocking(false);
                    //将当前的socketChannel注册到selector 关注事件为OP_READ 绑定buffer
                    socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }
                if (key.isReadable()){
                    //通过key 反向获取对应的Channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该Channel关联的Buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form server："+new String(buffer.array()));
                }
                //手动从当前集合中移除SelectionKey
                iterator.remove();
            }
        }
    }
}
