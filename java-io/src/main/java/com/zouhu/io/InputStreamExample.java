package com.zouhu.io;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 字节流操作案例
 * <p>
 *     字节流在操作文本文件的时候可能出现乱码问题，需要使用字符流进行操作
 * </p>
 *
 * @author zouhu
 * @data 2024-09-27 19:53
 */
public class InputStreamExample {
    public static void main(String[] args) {
        FileInputStream inputStream = null;

        try {
            // 创建字节输入流，读取文件
            inputStream = new FileInputStream("src/main/resources/example.txt");

            // 读取文件内容
            int content;
            while ((content = inputStream.read()) != -1) {
                // 输出到控制台 (按字节读取)
                System.out.print((char) content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
