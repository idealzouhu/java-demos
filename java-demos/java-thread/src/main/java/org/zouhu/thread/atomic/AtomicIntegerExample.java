package org.zouhu.thread.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示 AtomicInteger 进行线程安全的数值计数
 *
 * @author zouhu
 * @version 1.0
 * @data 2024-07-24 19:54
 */
public class AtomicIntegerExample {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                int newValue = counter.incrementAndGet();
                System.out.println("New value: " + newValue);
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) ;
        System.out.println("Final value: " + counter.get());
    }
}
