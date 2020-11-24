package com.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannelDemo04 {
    public static void main(String[] args) {
        //创建相关的流
        try (
                final FileInputStream fis = new FileInputStream("d:\\a.jpg");
                final FileOutputStream fos = new FileOutputStream("d:\\b.jpg");
                final FileChannel fisChannel = fis.getChannel();
                final FileChannel fosChannel = fos.getChannel();
        ) {
            fosChannel.transferFrom(fisChannel,0,fisChannel.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
