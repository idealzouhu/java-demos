package org.zouhu.thread.basic;

/**
 * 检查线程在不同状态下的变化
 *
 * @author zouhu
 * @version 1.0
 * @data 2024-07-15 22:23
 */
public class State {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Thread is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("State before start: " + thread.getState()); // NEW

        thread.start();
        System.out.println("State after start: " + thread.getState()); // RUNNABLE

        try {
            Thread.sleep(500);
            System.out.println("State after sleeping: " + thread.getState()); // TIMED_WAITING
            thread.join();
            System.out.println("State after join: " + thread.getState()); // TERMINATED
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
