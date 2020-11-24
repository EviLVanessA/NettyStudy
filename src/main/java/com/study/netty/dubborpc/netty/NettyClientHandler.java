package com.study.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    //上下文
    private ChannelHandlerContext context;
    //将来调用后返回的结果
    private String result;
    //客户端调用方法时传入的参数
    private String param;

    /**
     * 收到服务器的数据后，就会调用该方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        //唤醒等待的线程
        notify();
    }

    /**
     * 与服务器的连接创建成功后就会被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //在其他方法使用时会使用到ctx
        this.context = ctx;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务器，然后wait 然后等待被唤醒
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        //进行wait 等待channelRead方法获取到服务器的结果后唤醒
        wait();
        return result;
    }


    void setParam(String param){
        this.param = param;
    }
}
