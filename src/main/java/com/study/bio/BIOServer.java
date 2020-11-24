package com.study.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class BIOServer {
    public static void main(String[] args) {
        //1.创建一个线程池
        //2.如果客户端有连接，就创建一个线程与之通信(单独写一个方法)
        ExecutorService threadPool = new ThreadPoolExecutor(0, 100,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        //创建一个ServerSocket

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("Server is Starting");
            while (true){
                //监听客户端
                final Socket socket = serverSocket.accept();
                System.out.println("连接一个客户端");
                //创建线程与之通讯

                threadPool.execute(() -> {
                    //可以和客户端进行通讯
                    handler(socket);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //编写handler方法
    public static void handler(Socket socket){
        try {
            //用来接收数据
            byte[] bytes = new byte[1024];
            //通过socket来获取一个输入流
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端的数据
//            while (true){
//                int read = inputStream.read(bytes);
//                if (read != -1){
//                    //输出客户端发送的数据
//                    System.out.println(new String(bytes,0,read));
//                }else {
//                    break;
//                }
//            }
            int len = 0;
            while ((len = inputStream.read(bytes,0,bytes.length)) != -1){
                System.out.println(new String(bytes));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭客户端的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
