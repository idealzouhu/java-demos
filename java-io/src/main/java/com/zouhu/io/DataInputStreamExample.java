package com.zouhu.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * @author zouhu
 * @data 2024-09-27 20:26
 */
public class DataInputStreamExample {
    public static void main(String[] args) {
        String filename = "src/main/resources/data.bin";

        // 使用 DataOutputStream 写入基本数据类型
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            // 写入不同类型的数据
            dos.writeInt(42);            // 写入一个整数
            dos.writeDouble(3.14159);    // 写入一个双精度浮点数
            dos.writeBoolean(true);       // 写入一个布尔值
            dos.writeUTF("Hello World");  // 写入一个字符串
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("数据已写入到 " + filename);

        // 使用 DataInputStream 读取数据
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            int intValue = dis.readInt();        // 读取整数
            double doubleValue = dis.readDouble(); // 读取双精度浮点数
            boolean booleanValue = dis.readBoolean(); // 读取布尔值
            String stringValue = dis.readUTF();    // 读取字符串

            // 输出读取的数据
            System.out.println("整数: " + intValue);
            System.out.println("双精度浮点数: " + doubleValue);
            System.out.println("布尔值: " + booleanValue);
            System.out.println("字符串: " + stringValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
