package org.zouhu.thread.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 验证InheritableThreadLocal 的动态上下文传递问题
 *
 * @author zouhu
 * @data 2024-09-16 22:29
 */
public class InheritableThreadLocalProblem {
    // 定义 InheritableThreadLocal，用来存储用户会话信息
    private static InheritableThreadLocal<String> userSession = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        // 模拟父线程（即 main 线程）中设置用户会话信息
        userSession.set("User-12345");

        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 提交多个任务到线程池，模拟子线程获取父线程的会话信息
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                // 子线程中获取用户会话信息
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
