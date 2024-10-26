package org.zouhu.thread.basic;

/**
 * 测试 join 方法
 *
 * @author zouhu
 * @data 2024-08-29 16:22
 */
public class JoinExample {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                try {
                    Thread.sleep(1000);  // 模拟耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        t1.start(); // 启动第一个线程
        t1.join(); // 主线程等待 t1 线程执行完毕
        System.out.println("t1 has finished.");

        t2.start(); // 启动第二个线程
        t2.join(); // 主线程等待 t2 线程执行完毕
        System.out.println("t2 has finished.");

        System.out.println("All threads have finished.");
    }
}
