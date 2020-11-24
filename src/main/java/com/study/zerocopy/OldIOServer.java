package com.study.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统IO的服务器
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try{
                byte[] byteArray = new byte[4096];
                while (true){
                    int readCount = dataInputStream.read(byteArray,0,byteArray.length);
                    if (-1 == readCount){
                        break;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                dataInputStream.close();
                socket.close();
            }
        }
    }
}
