package com.zouhu.lock.IntrinsicLock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 不安全的计数器
 * <p>
 *     展示一个可能的不安全累加器实现，并给出相应的测试用例来说明在多线程环境下可能出现的问题。
 *     为什么加法运算的操作不是原子性的？加法运算至少包含三个 JVM 指令：”内存取值“，“加法运算“，”内存赋值“。
 * <p>
 *     为什么实际结果一定低于预期结果？当两个或更多的线程访问和修改同一份数据，并且最终结果取决于线程执行的具体顺序时，
 *     就会产生竞态条件。在计数器累加的场景中，如果多个线程几乎同时读取计数器的当前值，然后各自独立地进行加一操作并写回，
 *     最终写回的操作可能会覆盖其他线程的更新，导致某些增加操作丢失。
 *
 * @author zouhu
 * @data 2024-08-06 17:41
 */
public class UnsafeAccumulator {
    private int sum = 0;

    public void add(int value) {
        sum += value;
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
        UnsafeAccumulator accumulator = new UnsafeAccumulator();
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
