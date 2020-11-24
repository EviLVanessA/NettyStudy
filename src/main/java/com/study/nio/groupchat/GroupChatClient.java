package com.study.nio.groupchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class GroupChatClient {
    //服务器IP
    private final String HOST = "127.0.0.1";
    //服务器PORT
    private final int PORT = 6667;
    //创建selector
    private Selector selector;
    //客户端通道
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try{
            //拿到selector
            selector = Selector.open();
            //连接服务器,获取Channel
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);
            //注册到selector
            socketChannel.register(selector, SelectionKey.OP_READ);
            //得到username
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "\tis ok....");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //向服务器发送消息
    public void sendInfo(String info){
        info = username + "say:" + info;
        try{
            //向buffer中写入消息
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //读取服务端回复的消息
    public void readInfo(){
        try{
            int readChannels = selector.select();
            //有可用的通道
            if (readChannels > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if (next.isReadable()){
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) next.channel();
                        //得到Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //从sc中读取数据到buffer
                        sc.read(buffer);
                        //把缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            }else {
//                System.out.println("没有可用的通道");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //启动客户端 每三秒读取一次数据
        GroupChatClient chatClient = new GroupChatClient();
        new Thread(() -> {
            while (true){
                chatClient.readInfo();
                try{
                    Thread.sleep(3000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        },"client").start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            chatClient.sendInfo(str);
        }
    }
}
