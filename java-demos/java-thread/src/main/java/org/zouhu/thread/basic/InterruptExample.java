package org.zouhu.thread.basic;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 测试 interrupt 方法
 *
 * @author zouhu
 * @data 2024-08-29 16:15
 */
public class InterruptExample {
    static class MyThread extends Thread {
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    // 检查当前线程是否被中断
                    if (Thread.interrupted()) {
                        System.out.println("Thread was interrupted!");
                        return;
                    }
                    // 模拟长时间任务
                    System.out.println("Doing work: " + i);
                    Thread.sleep(1000); // 阻塞方法
                }
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted during sleep");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread();
        t.start();

        // 主线程等待 3 秒钟
        Thread.sleep(3000);

        // 中断子线程
        t.interrupt();
    }

}
