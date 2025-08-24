package com.qiantangnotes.nio.buffer;

import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ByteBufferTest {
    public static void main(String[] args) {
        //FileChannel
        try (FileChannel channel = new FileInputStream(new ClassPathResource("data.txt").getFile()).getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            //从channel中读取数据, 即写入buffer中
            while (channel.read(buffer) != -1) {
                //切换buffer的读模式
                buffer.flip();
                //检查buffer中是否还有剩余的数据
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                //切换到写模式
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
