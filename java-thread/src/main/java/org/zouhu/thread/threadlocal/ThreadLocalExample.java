package org.zouhu.thread.threadlocal;

/**
 * @author zouhu
 * @data 2024-08-29 18:16
 */
public class ThreadLocalExample {

    // 创建一个 ThreadLocal 变量
    private static final ThreadLocal<Integer> threadLocalValue = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        // 启动多个线程，展示 ThreadLocal 的线程隔离特性
        Runnable task = () -> {
            // 每个线程都设置自己的值
            threadLocalValue.set((int) (Math.random() * 100));

            // 获取当前线程的值
            Integer value = threadLocalValue.get();
            System.out.println("Thread " + Thread.currentThread().getName() + " has value: " + value);

            // 清除当前线程的值
            threadLocalValue.remove();
        };

        // 启动线程
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

