package com.qiantangnotes.nio.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class WalkFileTreeTest {
    public static void main(String[] args) throws IOException {
        AtomicInteger dirCount = new AtomicInteger(0);
        AtomicInteger fileCount = new AtomicInteger(0);
        Files.walkFileTree(Paths.get("/Users/source"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dir count: " + dirCount.get());
        System.out.println("file count: " + fileCount.get());
    }
}
