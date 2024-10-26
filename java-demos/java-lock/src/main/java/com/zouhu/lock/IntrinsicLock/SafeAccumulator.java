package com.zouhu.lock.IntrinsicLock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 安全累加器
 * <p>
 *     线程安全,利用synchronized关键字
 * </p>
 *
 * @author zouhu
 * @data 2024-08-06 18:01
 */
public class SafeAccumulator {
    private int sum;

    /**
     * 利用synchronized关键字实现线程安全
     *
     * @param value 要累加的值
     */
    public synchronized void add(int value) {
        sum += value;
    }

    public int getSum() {
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {
        SafeAccumulator accumulator = new SafeAccumulator();
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
