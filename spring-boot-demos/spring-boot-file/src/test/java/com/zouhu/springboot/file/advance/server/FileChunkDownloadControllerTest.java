package com.zouhu.springboot.file.advance.server;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static org.junit.jupiter.api.Assertions.*;

class FileChunkDownloadControllerTest {

    @Test
    void downloadFile() {
        File file = new File("D:\\Program Files\\ffmpeg-release-essentials.7z");
        long position = 100;  // 偏移量，从第100个字节开始读取
        int length = 50;      // 读取50个字节的内容

        try (FileInputStream fis = new FileInputStream(file);
             FileChannel fileChannel = fis.getChannel()) {

            // 创建一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(length);

            // 定位到指定位置
            fileChannel.position(position);

            // 读取指定长度的内容
            int bytesRead = fileChannel.read(buffer);

            if (bytesRead != -1) {
                // 将读取的内容转换为字符串输出
                buffer.flip();  // 切换到读模式
                String result = new String(buffer.array(), 0, bytesRead);
                System.out.println("Read content: " + result);
            } else {
                System.out.println("Reached end of file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}