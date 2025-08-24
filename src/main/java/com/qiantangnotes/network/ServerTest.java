package com.qiantangnotes.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        System.out.println("open a socket channel");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            System.out.println("connecting...");
            SocketChannel socketChannel = ssc.accept();
            System.out.println("accepted " + socketChannel);
            channels.add(socketChannel);
            for (SocketChannel channel : channels) {
                System.out.println("before read " + channel);
                channel.read(buffer);
                buffer.flip();
                System.out.println(StandardCharsets.UTF_8.decode(buffer));
                buffer.clear();
                System.out.println("after read " + channel);
            }
        }
    }
}
