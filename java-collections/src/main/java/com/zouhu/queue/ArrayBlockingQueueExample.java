package com.zouhu.queue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 测试 ArrayBlockingQueue
 *
 * @author zouhu
 * @data 2024-08-28 15:49
 */
public class ArrayBlockingQueueExample {
    public static void main(String[] args) {
        System.out.println("ArrayBlockingQueueExample");
        Queue<Integer> queue = new ArrayBlockingQueue<>(3);

    }
}
