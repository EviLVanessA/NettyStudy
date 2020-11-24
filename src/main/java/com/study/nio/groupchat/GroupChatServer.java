package com.study.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 聊天室服务端
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;
    //编写构造器

    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //初始化ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口号
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞问题
            listenChannel.configureBlocking(false);
            //将listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建一个服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        //进行监听
        groupChatServer.listen();
    }

    public void listen(){
        try {
            //循环监听
            while (true){
                int count = selector.select();
                //有事件处理
                if (count > 0){
                    //遍历selectionKey
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出selectionKey
                        SelectionKey key = iterator.next();
                        //如果监听的是ACCEPT
                        if (key.isAcceptable()){
                            //为客户端分配SocketChannel
                            SocketChannel socketChannel = listenChannel.accept();
                            //设置非阻塞
                            socketChannel.configureBlocking(false);
                            //将此socketChannel注册到selector上
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "\t上线啦.....");
                        }
                        //发生read事件
                        if (key.isReadable()){
                            //处理读
                            readData(key);
                        }
                        //将当前的key删除 防止重复处理
                        iterator.remove();
                    }
                }else {
                    System.out.println("Waiting");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 读取数据
     */
    private void readData(SelectionKey selectionKey) throws IOException {
        //定义一个socketChannel
        SocketChannel socketChannel = null;
        try{
            //取到关联的Channel
            socketChannel = (SocketChannel) selectionKey.channel();
            //创建缓冲
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int readCount = socketChannel.read(buffer);
            //根据readCount的值作进一步的处理
            if (readCount > 0){
                String msg = new String(buffer.array());
                System.out.println("Form Server\t"+msg);
                //向其他的客户端转发消息
                sendMsgToOtherClient(msg,socketChannel);
            }
        }catch(Exception e){
            try {
                System.out.println(socketChannel.getRemoteAddress() + "\t下线啦");
                //取消注册
                selectionKey.channel();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally{
            //关闭资源
            if (socketChannel != null){
                socketChannel.close();
            }
        }
    }

    private void sendMsgToOtherClient(String msg,SocketChannel selfChannel) throws Exception{
        System.out.println("服务器转发消息中");
        //遍历所有注册到selector上的Channel
        for (SelectionKey selectionKey : selector.keys()) {
            //通过key取到selectKeyChannel
            Channel targetChannel = selectionKey.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != selfChannel) {
                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将Buffer的数据写入通道
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }
}
