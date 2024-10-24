package com.zouhu.io;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author zouhu
 * @data 2024-09-27 19:57
 */
public class ReaderExample {
    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("src/main/resources/example.txt")) {
            int character;
            while ((character = fileReader.read()) != -1) {
                System.out.print((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
