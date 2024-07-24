package org.zouhu.thread.create;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        // 异步执行的具体逻辑
        return "Task executed";
    }

    public static void main(String[] args) throws Exception {
        MyCallable myCallable = new MyCallable();
        FutureTask<String> futureTask = new FutureTask<>(myCallable);

        // 使用 `FutureTask` 实例作为 `Thread` 构造器的 target 入参，构造新的 `Thread` 线程实例
        Thread thread = new Thread(futureTask, "returnableThread");

        // 启动新线程
        thread.start();

        try {
            // 阻塞性地获得并发线程的执行结果
            String result = futureTask.get();
            System.out.println("Result from callable: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
