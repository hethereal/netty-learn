package com.qiantangnotes.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    // 向客户端发送大量数据
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 3000000; i++) {
                        builder.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(builder.toString());
                    //实际写入的字节数
                    int write = sc.write(buffer);
                    System.out.println(write);
                    if (buffer.hasRemaining()) {
                        scKey.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                        //把未写完的数据挂到scKey上
                        scKey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println(write);
                    if (!buffer.hasRemaining()) {
                        //清除buffer
                        key.attach(null);
                        //取消可写时间的关注
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        }
    }
}
