package com.zouhu.string;

/**
 * @author zouhu
 * @data 2024-08-27 17:08
 */
public class StringBuiderExample {

    /**
     * 测试StringBuilder的线程安全性
     *
     * @throws InterruptedException
     */
    public static void testStringBuilderThreadSafety() throws InterruptedException {
        final int numThreads = 2;
        final int iterationsPerThread = 10000;

        StringBuilder builder = new StringBuilder();

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

    /**
     * 测试String和StringBuilder的性能
     */
    public static void time(){
        long startTime, endTime;

        // 使用String进行多次拼接
        startTime = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += "a";
        }
        endTime = System.currentTimeMillis();
        System.out.println("使用String拼接耗时: " + (endTime - startTime) + "ms");

        // 使用StringBuilder进行多次拼接
        startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("a");
        }
        endTime = System.currentTimeMillis();
        System.out.println("使用StringBuilder拼接耗时: " + (endTime - startTime) + "ms");
    }

    public static void main(String[] args){

        // time();

        try {
            testStringBuilderThreadSafety();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
