package com.zouhu.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 测试 CopyOnWriteArrayList 线程安全
 *
 * @author zouhu
 * @data 2024-08-22 18:51
 */
public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        List<Integer> list = new CopyOnWriteArrayList<>();
        // List<Integer> list = new ArrayList<>();

        // 添加元素
        list.add(1);
        list.add(2);
        list.add(3);

        // 创建一个线程修改列表
        Thread modifyThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                list.add(i);
                System.out.println("Added: " + i);
            }
        });

        // 创建一个线程遍历列表
        Thread readThread = new Thread(() -> {
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.println("Read: " + iterator.next());
            }
        });

        modifyThread.start();
        readThread.start();
    }
}
