package com.qiantangnotes.buffer;

import java.nio.ByteBuffer;

public class ByteBufferAllocateTest {
    public static void main(String[] args) {
        Class<? extends ByteBuffer> aClass = ByteBuffer.allocate(16).getClass();
        System.out.println(aClass);
        Class<? extends ByteBuffer> aClass1 = ByteBuffer.allocateDirect(16).getClass();
        System.out.println(aClass1);
    }
}
