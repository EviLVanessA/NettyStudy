package com.study.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferDemo {
    public static void main(String[] args) {
        try (
                RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
                final FileChannel channel = randomAccessFile.getChannel()
        ) {
            //实际为DirectByteBuffer 不包括5 是映射到内存的大小
            final MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            map.put(0,(byte)'H');
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
