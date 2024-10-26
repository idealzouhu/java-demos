package com.zouhu.lock.CAS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 利用 AtomicInteger 实现安全计数器
 * <p>
 *      AtomicInteger 底层依然使用的是 CAS 算法
 * </p>
 *
 * @author zouhu
 * @data 2024-08-07 13:01
 */
public class SafeAccumulatorOne {
    private AtomicInteger sum = new AtomicInteger(0);

    public void add(int value) {
        sum.getAndAdd(value);
    }

    public int getSum() {
        return sum.get();
    }

    public static void main(String[] args) throws InterruptedException {
        SafeAccumulatorOne accumulator = new SafeAccumulatorOne();
        final int threadCount = 100;
        final int incrementValue = 1;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    accumulator.add(incrementValue);
                }
                latch.countDown();
            });
        }

        latch.await(); // 等待所有线程完成
        executor.shutdown();

        // 预期结果应该是 100 * 100 * incrementValue
        System.out.println("Expected: " + (100 * 100 * incrementValue));
        System.out.println("Actual: " + accumulator.getSum());
    }
}
