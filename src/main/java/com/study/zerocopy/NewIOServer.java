package com.study.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket socket = serverSocketChannel.socket();

        socket.bind(new InetSocketAddress(7001));

        //创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            SocketChannel socketChannel1 = serverSocketChannel.accept();
            int readCount = 0;
            while ( -1 != readCount){
                try{
                    readCount = socketChannel1.read(buffer);
                }catch(Exception e){
                    e.printStackTrace();
                }
                //将buffer倒带 mark作废
                buffer.rewind();
            }
        }
    }
}
