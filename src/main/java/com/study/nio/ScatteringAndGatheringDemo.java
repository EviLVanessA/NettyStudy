package com.study.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering:将数据写入到Buffer时，可以采用buffer数组，依次写入
 * Gathering:从buffer中读出到数据时，可以采用buffer数组，依次读出
 */
public class ScatteringAndGatheringDemo {
    public static void main(String[] args) {
        final InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //绑定端口到Socket，并启动
            serverSocketChannel.socket().bind(inetSocketAddress);
            //创建一个Buffer数组
            final ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);
            //等客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            //循环读取
            while (true){
                int read = 0;
                while (read < 8){
                    final long read1 = socketChannel.read(byteBuffers);//写入到Buffer
                    read += read1;
                    System.out.println(read);
                    //使用流进行打印
                    Arrays.stream(byteBuffers)
                            .map(buffer -> "position:" + buffer.position() + "\tlimit:" + buffer.limit())
                            .forEach(System.out::println);
                }

                //将所有的Buffer进行翻转
                Arrays.asList(byteBuffers).forEach(Buffer::flip);
                //将数据读出显示到客户端
                long byteWrite = 0;
                while (byteWrite < 8){
                    final long write = socketChannel.write(byteBuffers);//从buffer中拿出
                    byteWrite += write;
                }

                //将所有的Buffer进行clear
                Arrays.asList(byteBuffers).forEach(Buffer::clear);
                System.out.println("byteRead:" + read + "\tbyteWrite" + byteWrite + "\tmessageLength\t" + 8 );
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
