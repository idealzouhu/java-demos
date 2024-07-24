package org.zouhu.thread.create;

/**
 * @author zouhu
 * @version 1.0
 * @data 2024-07-19 14:31
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {

    public static void main(String[] args) {
        // 创建一个包含3个线程的固定大小线程池
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 提交10个任务给线程池
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Task ID: " + taskId + " is running by " + Thread.currentThread().getName());
                    try {
                        // 假设每个任务需要1秒的时间来完成
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Task interrupted");
                    }
                }
            });
        }

        // 关闭线程池，等待所有任务完成
        executor.shutdown();
        try {
            // 等待所有任务结束，或超时后退出
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Tasks interrupted");
        }

        System.out.println("All tasks completed.");
    }
}

