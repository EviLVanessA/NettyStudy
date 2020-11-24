package com.study.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * @param ctx 上下文
     * @param evt 传递的事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            //将Event向下转型为IdleStatement
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE: eventType = "读空闲";break;
                case WRITER_IDLE: eventType = "写空闲";break;
                case ALL_IDLE: eventType = "读写空闲";break;
            }
            System.out.println(ctx.channel().remoteAddress() + "\t超时\t" + eventType);
            System.out.println("服务器处理");
        }
    }
}
