package org.zouhu.thread.threadpool;

import java.util.concurrent.*;

/**
 * 线程池使用案例
 *
 * @author zouhu
 * @data 2024-07-19 19:50
 */
public class ThreadPoolExample {
    public static void main(String[] args) {
        /**
         *  1.直接使用构造函数创建线程池
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // 核心线程数
                4, // 最大线程数
                10, // 空闲线程存活时间
                TimeUnit.SECONDS, // 时间单位
                new ArrayBlockingQueue<>(3), // 任务队列
                Executors.defaultThreadFactory(), // 线程工厂
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );

        // 提交任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task ID: " + taskId + " is running on thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 关闭线程池
        executor.shutdown();


        /**
         * 2.使用Executors创建线程池(不推荐使用)
         */
//        ExecutorService executor = Executors.newFixedThreadPool(5); // 创建固定大小的线程池
//
//        // 提交任务
//        for (int i = 0; i < 10; i++) {
//            final int taskId = i;
//            executor.execute(() -> {
//                System.out.println("Task ID: " + taskId + " is running on thread: " + Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            });
//        }
//
//        // 关闭线程池
//        executor.shutdown();
    }
}
