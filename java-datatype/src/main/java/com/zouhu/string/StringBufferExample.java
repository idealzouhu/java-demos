package com.zouhu.string;

/**
 * @author zouhu
 * @data 2024-08-27 17:21
 */
public class StringBufferExample {

    public static void main(String[] args) throws InterruptedException {
        final int numThreads = 2;
        final int iterationsPerThread = 10000;

        StringBuffer builder = new StringBuffer();

        Runnable appendTask = () -> {
            for (int i = 0; i < iterationsPerThread; i++) {
                builder.append("a");
            }
        };

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(appendTask);
            threads[i].start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final string length: " + builder.length());
    }
}
