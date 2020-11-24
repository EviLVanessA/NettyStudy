package com.study.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder的decode方法被调用");
        //需要将得到的二进制字节码转换成MessageProtocol
        int i = in.readInt();
        byte[] content = new byte[i];
        in.readBytes(content);
        //封装成MessageProtocol对象放入out进行下一步处理
        MessageProtocol messageProtocol = new MessageProtocol(i,content);
        //放入out进行下一步处理
        out.add(messageProtocol);
    }
}
