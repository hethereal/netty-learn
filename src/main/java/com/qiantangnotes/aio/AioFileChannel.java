package com.qiantangnotes.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

public class AioFileChannel {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 用于等待异步操作完成的计数器
        CountDownLatch latch = new CountDownLatch(1);

        // 手动创建通道，不使用try-with-resources（避免自动关闭）
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(
                Paths.get("data.txt"),
                StandardOpenOption.READ
        );
        System.out.println("开始读取（异步）");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 发起异步读取
        channel.read(buffer, 0, buffer, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                try {
                    System.out.println("Read completed: " + result + " 字节");
                    if (result != -1) { // -1表示已读完
                        attachment.flip();
                        System.out.println("内容：" + StandardCharsets.UTF_8.decode(attachment));
                    }
                } finally {
                    try {
                        // 操作完成后手动关闭通道
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 通知主线程：异步操作已完成
                    latch.countDown();
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close(); // 失败时也需要关闭通道
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Read failed: " + exc.getMessage());
                exc.printStackTrace();
                latch.countDown(); // 无论成功失败，都通知主线程
            }
        });

        System.out.println("异步读取已发起（此时可能尚未完成）");

        // 主线程等待异步操作完成（最多等10秒）
        latch.await();
        System.out.println("程序结束");
    }
}
