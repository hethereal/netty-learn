package com.qiantangnotes.nio.buffer;

import java.nio.ByteBuffer;

public class ByteBufferReadTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd', 'e', 'f'});
        buffer.flip();
        //从头开始读
/*        buffer.rewind();
        System.out.println("buffer.get() = " + (char) buffer.get());*/
        // mark & reset
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        buffer.mark(); //加标记，索引2的位置
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        buffer.reset(); // 将position重置到索引2
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
    }
}
