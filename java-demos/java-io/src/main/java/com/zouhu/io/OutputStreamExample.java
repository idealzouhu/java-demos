package com.zouhu.io;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zouhu
 * @data 2024-09-27 19:55
 */
public class OutputStreamExample {
    public static void main(String[] args) {
        FileOutputStream outputStream = null;

        try {
            // 创建字节输出流，写入文件
            outputStream = new FileOutputStream("src/main/resources/output.txt");

            // 要写入的字符串
            String content = "Hello, Byte Stream!";

            // 将字符串转换为字节数组
            byte[] contentBytes = content.getBytes();

            // 写入字节数组
            outputStream.write(contentBytes);

            System.out.println("写入完成！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
