package com.qiantangnotes.nio.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class ServerBySelector {
    public static void main(String[] args) throws IOException {
        // 1. 创建Selector 可以管理多个Channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //将channel注册到Selector
        // 事件发生后通过SelectionKey可以知道哪个事件和哪个channel发生的
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //指明只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            // 没有事件时阻塞，有事件发生后线程才会恢复
            selector.select();
            // 处理事件 selectedKeys包含了所有发生的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //必须移除，否则会产生空指针异常
                iterator.remove();
                // 区分事件类型
                if (key.isAcceptable()) {
                    System.out.println("key: " + key);
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    System.out.println("sc: " + sc);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int read = channel.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            buffer.flip();
                            System.out.println("buffer: " + StandardCharsets.UTF_8.decode(buffer));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }
}
