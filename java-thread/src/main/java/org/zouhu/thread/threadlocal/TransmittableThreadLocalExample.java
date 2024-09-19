package org.zouhu.thread.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zouhu
 * @data 2024-09-16 21:58
 */
public class TransmittableThreadLocalExample {
    // 创建一个 TransmittableThreadLocal，用于存储用户会话信息
    private static TransmittableThreadLocal<String> userSession = new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        // 在父线程中设置用户会话信息
        userSession.set("User-12345");

        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 使用 TtlExecutors 包装线程池，使其避免线程池之间的数据传递问题
        executorService = TtlExecutors.getTtlExecutorService(executorService);

        // 提交任务到线程池，模拟子线程获取父线程的会话信息
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                // 子线程可以访问父线程的值
                System.out.println(Thread.currentThread().getName() + ": " + userSession.get());
            });
        }

        // 父线程更新值
        userSession.set("User-67890");

        // 提交更多任务，验证父线程最新值是否传递给子线程
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                // 子线程获取父线程最新值
                System.out.println(Thread.currentThread().getName() + ": " + userSession.get());
            });
        }

        // 关闭线程池
        executorService.shutdown();
    }
}
