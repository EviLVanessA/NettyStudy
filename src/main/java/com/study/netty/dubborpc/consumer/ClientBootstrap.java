package com.study.netty.dubborpc.consumer;

import com.study.netty.dubborpc.netty.NettyClient;
import com.study.netty.dubborpc.publicinterface.HelloService;

public class ClientBootstrap {
    public static final String provider = "HelloService#hello#";
    public static void main(String[] args) {
        //创建一个消费者
        NettyClient consumer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) consumer.getBean(HelloService.class, provider);
        //通过代理对象调用服务提供者的方法
        String res = service.hello("hello Dubbo");
        System.out.println("调用的结果 res = " + res);
    }
}
