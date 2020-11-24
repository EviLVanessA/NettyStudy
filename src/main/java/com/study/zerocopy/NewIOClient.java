package com.study.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String fileName = "test.zip";
        //得到文件的Channel
        FileChannel channel = new FileInputStream(fileName).getChannel();
        //准备发送
        long startTime = System.currentTimeMillis();
        //在linux下，一个TransferTo方法就可以完成传输 再win下最多8M的文件 就需要分段传输文件，而且要注意传输的位置
        //并且transferTo底层使用了0拷贝
        long count = channel.transferTo(0, channel.size(), socketChannel);
        System.out.println("发送的总字节数 " + count + " 花费时间  " + (System.currentTimeMillis() - startTime) + "毫秒");
        channel.close();
    }
}
