package com.zouhu.io;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zouhu
 * @data 2024-09-27 19:57
 */
public class WriterExample {
    public static void main(String[] args) {
        try (FileWriter fileWriter = new FileWriter("src/main/resources/output.txt")) {
            String content = "Hello, world!\nThis is a test text file.";
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
