package com.zouhu.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author zouhu
 * @data 2024-08-28 16:32
 */
public class DealyQueueExample {
    public static void main(String[] args) {
        // 创建 DelayQueue
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        // 添加任务到队列
        delayQueue.put(new DelayedTask("Task 1", 5000));
        delayQueue.put(new DelayedTask("Task 2", 3000));
        delayQueue.put(new DelayedTask("Task 3", 4000));

        // 消费者线程
        new Thread(() -> {
            while (true) {
                try {
                    // 取出延迟到期的任务
                    DelayedTask task = delayQueue.take();
                    System.out.println("Processing task: " + task.getName() + " at time: " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    // 自定义任务类实现 Delayed 接口
    static class DelayedTask implements Delayed {
        private final String name;
        private final long expireTimeMillis;

        public DelayedTask(String name, long delayTimeMillis) {
            this.name = name;
            this.expireTimeMillis = System.currentTimeMillis() + delayTimeMillis;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else {
                return 0;
            }
        }

        public String getName() {
            return name;
        }
    }
}
