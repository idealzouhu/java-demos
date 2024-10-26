package com.zouhu.lock.CAS;

import sun.misc.Unsafe;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.lang.reflect.Field;

/**
 * 利用CAS实现线程安全累加
 * <p>
 *     使用CAS无锁编程算法实现一个轻量级的安全自增实现版本. 每条线程通过 CAS 自旋对共享数据进行自增操作.
 * </p>
 *
 * @author zouhu
 * @data 2024-08-07 13:11
 */
public class SafeAccumalatorTwo {
    private volatile int sum = 0;
    private static final Unsafe unsafe;
    private static final long sumOffset;

    /**
     * 获取Unsafe实例
     * <p>
     *     静态初始化块（static initializer block）, 在类加载的时候执行，只执行一次。
     *     主要用于初始化类的静态成员变量，比如静态变量、静态方法等。
     * <p
     *     Unsafe是Java虚拟机提供的一个类，它提供了一些底层的、非Java语言的接口，允许Java程序直接调用操作系统的底层服务。
     * <p>
     *     通过反射获取Unsafe实例，以获取CAS操作所需的偏移量.
     *     获取偏移量是为了能够通过Unsafe的compareAndSwapInt方法来更新共享变量的值.
     *     通过Unsafe的objectFieldOffset方法获取共享变量sum的偏移量.
     *
     */
    static {
        try {
            // 获取反射的 Unsafe实例
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            // 获取共享变量sum的偏移量
            sumOffset = unsafe.objectFieldOffset
                    (SafeAccumalatorTwo.class.getDeclaredField("sum"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * 使用 CAS 自增
     * <p>
     *     add方法使用了while循环来尝试更新sum的值，直到成功为止.
     *     如果更新失败，则继续尝试，直到成功为止.
     *     其中，通过compareAndSwapInt方法实现了CAS操作，保证了累加操作的线程安全性。
     * </p>
     *
     * @param value
     */
    public void add(int value) {
        while (true) {
            int current = sum;
            if (unsafe.compareAndSwapInt(this, sumOffset, current, current + value)) {
                break;
            }
        }
    }

    public int getSum() {
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {
        SafeAccumalatorTwo accumulator = new SafeAccumalatorTwo();
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
