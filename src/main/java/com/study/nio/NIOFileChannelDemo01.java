package com.study.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannelDemo01 {
    public static void main(String[] args) throws Exception {
        String str = "hello java ....";
        //创建一个输出流 ->channel
        FileOutputStream fos = new FileOutputStream("d:\\file01.txt");
        //通过输出流获取对应的FileChannel NIO 中对输出流进行了包装
        //FileChannel的真实类型是FileChannelImpl
        FileChannel channel = fos.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将Str放入Buffer
        byteBuffer.put(str.getBytes());
        //反转Buffer
        byteBuffer.flip();
        //将byteBuffer的数据写入到Channel中
        channel.write(byteBuffer);

        fos.close();

    }
}
