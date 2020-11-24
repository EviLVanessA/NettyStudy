package com.study.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class NettyByteBufDemo02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world1", CharsetUtil.UTF_8);
        System.out.println(byteBuf.capacity());
//        for (int i = 0; i < byteBuf.capacity()-2; i++) {
//            System.out.println(byteBuf.readByte());
//        }
        if (byteBuf.hasArray()){
            byte[] array = byteBuf.array();
            System.out.println(new String(array,CharsetUtil.UTF_8));
        }
    }
}
