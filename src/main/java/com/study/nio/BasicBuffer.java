package com.study.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        //简单说明NIO的Buffer
        //创建一个Buffer，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //向Buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //将Buffer转换  读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
