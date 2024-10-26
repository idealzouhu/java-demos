package org.zouhu.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");

//        MyThread myThread = new MyThread();
//        myThread.start();   // 启动线程
//
        Class<?> clazz = Main.class;

        // 调用 Object 类的方法
        System.out.println(clazz.toString());      // 输出 Class 对象的字符串表示
        System.out.println(clazz.hashCode());      // 获取 Class 对象的哈希码
        System.out.println(clazz.equals(Main.class));  // 比较 Class 对象是否相等

    }
}