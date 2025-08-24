package com.qiantangnotes.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileChannelTransfer {
    public static void main(String[] args) {
        try (FileChannel from = new FileInputStream("data.txt").getChannel();
             FileChannel to = new FileOutputStream("data3.txt").getChannel()) {
            //效率高
            long size = from.size();
            for (long left = size; left > 0;) {
                left -= from.transferTo(0, size, to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
