package com.study.netty.dubborpc.provider;

import com.study.netty.dubborpc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        if (msg != null){
            return "Hello client [" + msg + "]";
        }else {
            return "Hello client";
        }
    }
}
