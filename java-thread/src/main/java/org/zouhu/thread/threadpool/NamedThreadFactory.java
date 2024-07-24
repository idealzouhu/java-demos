package org.zouhu.thread.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池命名工厂
 * <p>
 * 初始化线程池的时候需要显示命名（设置线程池名称前缀），有利于定位问题.
 * 当查看线程堆栈信息或进行日志记录时，可以更容易地识别出哪些线程属于哪个线程池，从而方便调试和性能分析。
 *
 * @author zouhu
 * @version 1.0
 * @data 2024-07-24 17:30
 */
public class NamedThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    /**
     * 构造方法
     *
     * @param poolName 线程池名称
     */
    public NamedThreadFactory(String poolName) {
        group = Thread.currentThread().getThreadGroup();
        namePrefix = poolName + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(
            3, // corePoolSize
            10, // maximumPoolSize
            60L, // keepAliveTime
            TimeUnit.SECONDS, // timeUnit
            new LinkedBlockingQueue<>(), // workQueue
            new NamedThreadFactory("MyCustomPool") // threadFactory
        );

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is running");
            });
        }

        executor.shutdown();
    }
}

