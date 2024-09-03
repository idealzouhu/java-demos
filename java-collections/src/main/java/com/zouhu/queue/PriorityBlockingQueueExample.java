package com.zouhu.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zouhu
 * @data 2024-08-28 16:56
 */
public class PriorityBlockingQueueExample {

    public static class TaskProducer implements Runnable {
        private final PriorityBlockingQueue<Task> queue;

        public TaskProducer(PriorityBlockingQueue<Task> queue) {
            this.queue = queue;
        }

        /**
         * 生产者线程，每隔一段时间生产一个任务
         */
        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    Task task = new Task(i, "Task " + i);
                    queue.put(task);
                    Thread.sleep((long) (Math.random() * 1000));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static class TaskConsumer implements Runnable {
        private final PriorityBlockingQueue<Task> queue;

        public TaskConsumer(PriorityBlockingQueue<Task> queue) {
            this.queue = queue;
        }

        /**
         * 消费者线程，从队列中取出任务进行处理
         */
        @Override
        public void run() {
            while (true) {
                try {
                    Task task = queue.take();
                    System.out.println("Processing task: " + task.getDescription());
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public static class Task implements Comparable<Task> {
        private int priority;
        private String description;

        public Task(int priority, String description) {
            this.priority = priority;
            this.description = description;
        }

        // 实现compareTo方法来定义优先级
        @Override
        public int compareTo(Task other) {
            return Integer.compare(this.priority, other.priority);
        }

        public int getPriority() {
            return priority;
        }

        public String getDescription() {
            return description;
        }
    }

    public static void main(String[] args) {
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 启动生产者线程
        executor.execute(new TaskProducer(queue));

        // 启动消费者线程
        for (int i = 0; i < 3; i++) {
            executor.execute(new TaskConsumer(queue));
        }

        // 等待所有任务完成
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
