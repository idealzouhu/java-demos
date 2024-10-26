package org.zouhu.thread.tools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zouhu
 * @data 2024-10-04 20:23
 */
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                // 模拟耗时操作
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName() + " 完成");
                latch.countDown();
            });
        }

        // 等待所有任务完成
        latch.await();

        System.out.println("所有任务完成，继续执行后续逻辑...");
        executor.shutdown();

    }
}
