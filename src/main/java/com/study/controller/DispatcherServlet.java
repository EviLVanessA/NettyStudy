package com.study.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 前端控制器，作用：①拦截所有请求 ②解析请求 ③派发给对应的Controller里边的方法进行处理
 *
 * @author jianghui
 * @date 2020-11-25 15:00
 */
@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("reqPath   " + req.getServletPath());
        System.out.println("reqMethod  " + req.getMethod());
    }
}
