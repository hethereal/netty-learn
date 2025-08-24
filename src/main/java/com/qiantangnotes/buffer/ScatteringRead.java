package com.qiantangnotes.buffer;

import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatteringRead {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(new ClassPathResource("data.txt").getFile()); FileChannel channel = fis.getChannel()) {
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(3);
            channel.read(new ByteBuffer[]{b1, b2, b3});
            b1.flip();
            b2.flip();
            b3.flip();
            while (b1.hasRemaining()) {
                System.out.println((char) b1.get());
            }
            while (b2.hasRemaining()) {
                System.out.println((char) b2.get());
            }
            while (b3.hasRemaining()) {
                System.out.println((char) b3.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
