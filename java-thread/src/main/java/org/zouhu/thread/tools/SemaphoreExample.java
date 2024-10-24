package org.zouhu.thread.tools;

import java.util.concurrent.Semaphore;

/**
 * 测试 Semaphore
 *
 * @author zouhu
 * @data 2024-09-20 20:10
 */
public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2); // 创建一个有2个许可的Semaphore

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " attempting to acquire...");
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired permit.");

                // 模拟访问共享资源
                Thread.sleep(2000);

                System.out.println(Thread.currentThread().getName() + " releasing permit.");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 创建并启动多个线程
        for (int i = 0; i < 4; i++) {
            new Thread(task).start();
        }
    }
}
