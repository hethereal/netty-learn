package com.qiantangnotes.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.configureBlocking(false);
        sc.write(Charset.defaultCharset().encode("1234123412341234\n"));
        sc.write(Charset.defaultCharset().encode("1234\n4321432143214321\n"));
        System.out.println("waiting ...");
    }
}
