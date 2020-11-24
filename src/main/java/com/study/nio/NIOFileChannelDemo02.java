package com.study.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannelDemo02 {
    public static void main(String[] args) {
        //创建文件输入流

        try (FileInputStream fis = new FileInputStream(new File("d:\\file01.txt"))) {
            FileChannel channel = fis.getChannel();
            //创建缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //将通道的数据读入到Buffer
            channel.read(buffer);
            //将字节转成字符串
            System.out.println(new String(buffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
