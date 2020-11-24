package com.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBufDemo01 {
    public static void main(String[] args) {
        //创建一个byteBuf 创建一个对象 该对象包含一个arr capacity为10 byte[10]
        //在netty中的byteBuf不需要使用flip进行反转 底层维护了readerIndex和 WriterIndex
        ByteBuf byteBuf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);


        }
//        for (int i = 0; i < byteBuf.capacity(); i++) {
//            System.out.println(byteBuf.getByte(i));
//        }
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }
    }
}
