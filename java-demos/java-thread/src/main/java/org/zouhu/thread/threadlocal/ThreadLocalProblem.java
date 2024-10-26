package org.zouhu.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 验证 ThreadLocal 的问题及其解决的方案
 *
 * @author zouhu
 * @data 2024-09-16 20:24
 */
public class ThreadLocalProblem {

    /**
     * 1. ThreadLocal 导致的内存泄漏
     */
    public static void memoryLeakExample() {
        // 创建一个 ThreadLocal 变量
        ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();

        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 提交 100 个任务到线程池
        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                // 设置 ThreadLocal 的值
                threadLocal.set(new byte[1024 * 1024 * 10]); // 10MB 的大对象

                try {
                    // 模拟长时间运行的任务
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 确保即使处理
                    // threadLocal.remove();
                }
            });
        }

        // 关闭线程池
        executor.shutdown();
    }

    /**
     * 2. 线程池中线程复用导致的 ThreadLocal 值共享问题
     */
    public static void threadPoolExample() {
        // 创建一个 ThreadLocal 变量
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 提交 100 个任务到线程池
        for (int i = 0; i < 1000; i++) {
            final int taskId = i; // 需要用 final 变量传递任务 ID
            executor.submit(() -> {
                // 设置 ThreadLocal 的值
                String expectedValue = "task-" + taskId;
                threadLocal.set(expectedValue);

                // 模拟长时间运行的任务，增加线程复用的概率
                try {
                    Thread.sleep(50); // 增加延迟
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // 检查 ThreadLocal 中的值是否为期望值
                String actualValue = threadLocal.get();
                if (!expectedValue.equals(actualValue)) {
                    System.err.println("变量污染: 线程[" + Thread.currentThread().getName() + "] 预期值: "
                            + expectedValue + ", 实际值: " + actualValue);
                } else {
                    System.out.println("线程[" + Thread.currentThread().getName() + "] 正确: " + actualValue);
                }

                // 注意：没有调用 remove()，故意制造变量污染
                // threadLocal.remove();
            });
        }

        // 关闭线程池
        executor.shutdown();
    }


    /**
     * 3. 子线程无法使用父线程的 ThreadLocal 变量
     *
     * @throws InterruptedException
     */
    public static void inheritableThreadLocalExample() throws InterruptedException {
        // 创建一个 ThreadLocal 变量
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        // 在父线程中设置值
        threadLocal.set("parent value");

        // 创建一个子线程
        Thread childThread = new Thread(() -> {
            // 子线程无法访问父线程设置的 ThreadLocal 值，输出为 null
            System.out.println("Child thread: " + threadLocal.get());
        });

        // 启动子线程
        childThread.start();

        // 等待子线程结束
        childThread.join();

        // 打印父线程中的值
        System.out.println("Parent thread: " + threadLocal.get());
    }

    public static void main(String[] args) throws InterruptedException {
        // 调用三个示例函数
        // memoryLeakExample();
        // threadPoolExample();
        inheritableThreadLocalExample();
    }
}
