package com.zouhu.lock;

import org.openjdk.jol.info.ClassLayout;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        // 立即执行一次同步操作以获取偏向锁
        Thread.sleep(4000);

        lock = new Object();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }
}