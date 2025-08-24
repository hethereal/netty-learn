package com.qiantangnotes.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *  假设客户端向服务器发送了3条消息：
 *  hello,world\n
 *  I'm zhangsan\n
 *  how are you?\n
 *  现在服务器接收时变成了：
 *  hello,world\nI'm zhangsan\nho
 *  w are you?\n
 *  请编写一个程序将消息恢复成原始数据
 */
public class ByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("hello,world\nI'm zhangsan\nho".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }
    public static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                target.flip();
                System.out.println(StandardCharsets.UTF_8.decode(target));
            }
        }
        source.compact();
    }
}
