package com.qiantangnotes.nio.buffer;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class GatheringRead {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");
        try (FileOutputStream fos = new FileOutputStream(new File("data2.txt")); FileChannel channel = fos.getChannel()) {
            long write = channel.write(new ByteBuffer[]{b1, b2, b3});
            System.out.println(write);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
