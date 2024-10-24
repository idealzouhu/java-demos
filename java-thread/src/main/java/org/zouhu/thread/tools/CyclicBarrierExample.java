package org.zouhu.thread.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zouhu
 * @data 2024-10-04 20:30
 */
public class CyclicBarrierExample {
    public static void main(String[] args) {

        // 创建 CyclicBarrier，指定 3 个线程，并且提供一个 Runnable，所有线程到达屏障时执行
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("所有线程都已到达屏障，开始下一轮任务");
        });

        // 创建任务
        Runnable task = () -> {
            try {
                // 模拟第一轮任务
                System.out.println(Thread.currentThread().getName() + " 执行第一轮任务");
                Thread.sleep(1000); // 模拟任务执行时间
                System.out.println(Thread.currentThread().getName() + " 到达第一轮屏障");
                barrier.await(); // 等待所有线程到达屏障

                // 模拟第二轮任务
                System.out.println(Thread.currentThread().getName() + " 执行第二轮任务");
                Thread.sleep(1000); // 模拟任务执行时间
                System.out.println(Thread.currentThread().getName() + " 到达第二轮屏障");
                barrier.await(); // 等待所有线程到达屏障

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        // 启动三个线程
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }
    }
}
