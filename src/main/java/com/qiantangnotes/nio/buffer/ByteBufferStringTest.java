package com.qiantangnotes.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferStringTest {
    public static void main(String[] args) {
        // 字符串转ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello world".getBytes());
        //charset: 会自动切换到读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello world");
        //wrap方法: 会自动切换到读模式
        ByteBuffer buffer3 = ByteBuffer.wrap("hello world".getBytes());
        // ByteBuffer转字符串
        String str2 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println("str2 = " + str2);
    }
}
