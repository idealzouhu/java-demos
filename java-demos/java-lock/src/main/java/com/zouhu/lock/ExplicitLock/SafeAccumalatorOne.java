package com.zouhu.lock.ExplicitLock;

import com.zouhu.lock.IntrinsicLock.UnsafeAccumulator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 实现安全累加器
 *
 * @author zouhu
 * @data 2024-08-07 16:56
 */
public class SafeAccumalatorOne {
    private int sum = 0;
    private Lock lock = new ReentrantLock();

    public void add(int value) {
        // Lock lock = new ReentrantLock(); // 会出现线程安全问题
        lock.lock();
        try {
            sum += value;
        } finally {
            lock.unlock();
        }
    }

    public int getSum() {
        return sum;
    }

    /**
     * 测试累加器
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        SafeAccumalatorOne accumulator = new SafeAccumalatorOne();
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
