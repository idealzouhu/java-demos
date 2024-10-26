package org.zouhu.thread.basic;

/**
 * 使用 wait 和 notify 来实现线程间的同步
 * <p>
 *     一个经典的使用案例是“生产者-消费者”模型。
 *     假设有一个缓冲区，生产者线程向缓冲区添加元素，而消费者线程从缓冲区移除元素。
 *     为了防止生产者在缓冲区满时继续添加元素，或者消费者在缓冲区空时尝试移除元素，
 *     我们可以使用 wait 和 notify 来控制线程的行为。
 * </p>
 *
 * @author zouhu
 * @data 2024-08-29 14:21
 */
public class ThreadCommunication {

    /**
     * 缓冲区类，用于存储和操作数据
     */
    public static class Buffer {
        private int data;
        private boolean empty = true;

        public synchronized void put(int data) {
            while (!this.empty) {
                try {
                    // 如果缓冲区非空，则等待
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // 设置数据，并标记缓冲区为非空
            this.data = data;
            this.empty = false;
            // 唤醒可能在等待的消费者线程
            this.notifyAll();
        }

        public synchronized int take() {
            while (this.empty) {
                try {
                    // 如果缓冲区为空，则等待
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            // 获取数据，并标记缓冲区为空
            this.empty = true;
            // 唤醒可能在等待的生产者线程
            this.notifyAll();
            return this.data;
        }
    }

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Producing: " + i);
                buffer.put(i);
                try {
                    Thread.sleep(100); // 让生产者稍微慢一点
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Producer interrupted");
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int value = buffer.take();
                System.out.println("Consuming: " + value);
                try {
                    Thread.sleep(200); // 让消费者更慢一些
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Consumer interrupted");
                }
            }
        });

        producer.start();
        consumer.start();
    }
}

