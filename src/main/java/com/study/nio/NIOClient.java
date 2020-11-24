package com.study.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //提供服务端的IP和端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("连接需要时间，客户端不会阻塞");
            }
        }

        //连接成功发送数据
        String str = "hello java";
        //根据字节数组到Buffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据 将buffer的数据写入到channel
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
