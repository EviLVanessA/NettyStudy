package com.study.netty.inboundandoutound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     *
     * @param ctx 上下文对象
     * @param in   入站的ByteBuf
     * @param out  List集合 将解码后的数据传给下一个InboundHandler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("进入decode方法");
        //以为long为8个字节
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
