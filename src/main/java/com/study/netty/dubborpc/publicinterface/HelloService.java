package com.study.netty.dubborpc.publicinterface;

/**
 * 这是个接口服务方和消费方共有的
 */
public interface HelloService {
    String hello(String msg);
}
