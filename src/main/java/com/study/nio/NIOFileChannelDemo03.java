package com.study.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannelDemo03 {
    public static void main(String[] args) {
        //使用一个Buffer拷贝文件
        try (
            FileInputStream fis = new FileInputStream(new File("1.txt"));
            FileOutputStream fos = new FileOutputStream(new File("2.txt"))
        ) {
            final FileChannel fisChannel = fis.getChannel();
            final FileChannel fosChannel = fos.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true){
                //这里有一个很重要的操作 标志位重置
                byteBuffer.clear();
                final int read = fisChannel.read(byteBuffer);
                if ( read == -1){
                    break;
                }
                byteBuffer.flip();
                fosChannel.write(byteBuffer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
